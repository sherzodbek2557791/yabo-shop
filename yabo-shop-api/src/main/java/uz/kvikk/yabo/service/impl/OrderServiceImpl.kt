package uz.kvikk.yabo.service.impl

import org.hashids.Hashids
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uz.kvikk.yabo.model.enums.OrderStatus
import uz.kvikk.yabo.model.model.Product
import uz.kvikk.yabo.model.transport.OrderDTO
import uz.kvikk.yabo.model.transport.OrderItemRequest
import uz.kvikk.yabo.model.transport.OrderRequest
import uz.kvikk.yabo.model.transport.OrderResponse
import uz.kvikk.yabo.service.BotService
import uz.kvikk.yabo.service.OrderService
import uz.kvikk.yabo.utils.converter.DateConverter
import uz.kvikk.yabo.utils.json
import java.util.*

@Service
class OrderServiceImpl(val hashids: Hashids, val dsl: DSLContext, val botService: BotService) : OrderService {

    @Transactional
    override fun create(orderRequest: OrderRequest): OrderResponse {
        val order = this.insertOrder(orderRequest)

        this.insertPayment(order, orderRequest)
        this.insertOrderItems(order, orderRequest.orderItems)

        var message = """
            Region: ${order.regionNameRuRu}
            Area: ${order.areaNameRuRu}
            Tel: ${orderRequest.phoneNumber}
            FIO: ${orderRequest.fullName}
            Payment type: ${orderRequest.paymentType}
            Installment plan: ${orderRequest.installmentPlan}
            Payer passport back: ${orderRequest.payerPassportBack}
            Payer passport front: ${orderRequest.payerPassportFront}
            Payer salary report: ${orderRequest.payerSalaryReport}
            Guarantor passport back: ${orderRequest.guarantorPassportBack}
            Guarantor passport front: ${orderRequest.guarantorPassportFront}
            Guarantor salary report: ${orderRequest.guarantorSalaryReport}
            Message: ${orderRequest.message}
        """.trimIndent()

        for ((index, item) in orderRequest.orderItems.withIndex()) {
            message += "\n" + """
                ${index + 1}: ${item.code}-${item.count}x${item.price}=${item.count * item.price}
                    ${item.title}
            """.trimIndent()
        }
        botService.sendOrder(message)

        return OrderResponse(
                orderRequest.orderItems,
                orderRequest.regionSoato,
                orderRequest.areaSoato,
                orderRequest.fullName,
                orderRequest.phoneNumber,
                orderRequest.paymentType,
                orderRequest.installmentPlan,
                orderRequest.payerPassportBack,
                orderRequest.payerPassportFront,
                orderRequest.payerSalaryReport,
                orderRequest.guarantorPassportBack,
                orderRequest.guarantorPassportFront,
                orderRequest.guarantorSalaryReport,
                orderRequest.message
        )
    }

    override fun oneOrder(code: String): OrderDTO {
        return dsl.fetchOne("""
            select o.ID,
                   o.CODE,
                   o.REGION_SOATO,
                   r.NAME_UZ_UZ as REGION_NAME_UZ_UZ,
                   r.NAME_RU_RU as REGION_NAME_RU_RU,
                   o.AREA_SOATO,
                   a.NAME_UZ_UZ as AREA_NAME_UZ_UZ,
                   a.NAME_RU_RU as AREA_NAME_RU_RU,
                   o.FULL_NAME,
                   o.PHONE_NUMBER,
                   p.PAYMENT_TYPE
            from orders o
                     left join region r on o.region_soato = r.soato
                     left join region a on o.area_soato = a.soato
                     left join order_payment p on o.id = p.order_id
            where o.code={0}
        """.trimIndent(), code)!!.into(OrderDTO::class.java)
    }

    @Transactional
    fun insertOrder(orderRequest: OrderRequest): OrderDTO {
        val orderId = dsl.select(ORDERS_SEQ.nextval()).fetchOne().into(Long::class.java)
        val orderCode = hashids.encode(orderId)
        dsl.insertInto(ORDERS,
                ORDERS_ID,
                ORDERS_CODE,
                ORDERS_PHONE_NUMBER,
                ORDERS_ORDER_STATUS,
                ORDERS_CREATED_DATE,
                FULL_NAME,
                REGION_SOATO,
                AREA_SOATO
        ).values(
                orderId,
                orderCode,
                orderRequest.phoneNumber,
                OrderStatus.NEW.name,
                Date(),
                orderRequest.fullName,
                orderRequest.regionSoato,
                orderRequest.areaSoato
        ).execute()
        return this.oneOrder(orderCode)
    }

    @Transactional
    fun insertPayment(order: OrderDTO, orderRequest: OrderRequest) {
        val paymentDetails = json {
            "paymentType" to orderRequest.paymentType
            "installmentPlan" to orderRequest.installmentPlan
            "payerPassportBack" to orderRequest.payerPassportBack
            "payerPassportFront" to orderRequest.payerPassportFront
            "payerSalaryReport" to orderRequest.payerSalaryReport
            "guarantorPassportBack" to orderRequest.guarantorPassportBack
            "guarantorPassportFront" to orderRequest.guarantorPassportFront
            "guarantorSalaryReport" to orderRequest.guarantorSalaryReport
        }

        val orderPaymentId = dsl.select(ORDER_PAYMENT_SEQ.nextval()).fetchOne().into(Long::class.java)
        dsl.execute("""
            insert into ORDER_PAYMENT (ID, ORDER_ID, PAYMENT_TYPE, PAYMENT_DETAILS, TOTAL_PRICE, FIRST_PAY, CREATED_DATE) 
            values ({0}, {1}, {2}, {3}::jsonb, {4}, {5}, now())
        """.trimIndent(),
                orderPaymentId,
                order.id,
                orderRequest.paymentType,
                paymentDetails.toString(),
                0.0,
                0.0)
    }

    @Transactional
    fun insertOrderItems(order: OrderDTO, orderItems: List<OrderItemRequest>) {
        for (item in orderItems) {
            val product = dsl.fetchOne("select * from PRODUCT where code={0}", item.code).into(Product::class.java)
            val orderItemId = dsl.select(ORDER_ITEM_SEQ.nextval()).fetchOne().into(Long::class.java)
            dsl.insertInto(ORDER_ITEM,
                    ORDER_ITEM_ID,
                    ORDER_ITEM_ORDER_ID,
                    ORDER_ITEM_PRODUCT_ID,
                    ORDER_ITEM_COUNT,
                    ORDER_ITEM_PRICE
            ).values(
                    orderItemId,
                    order.id,
                    product.id,
                    item.count,
                    product.price
            ).execute()
        }
    }


    companion object {
        val SELECT: String = """
           select p.code,
                   p.title,
                   p.description,
                   p.price,
                   p.currency,
                   p.image,
                   p.start_date
            from product p
            where 1=1
        """.trimIndent()

        val TOTAL: String = """
            select count(p.id)
            from product p
            where 1=1
        """.trimIndent()

        val ORDERS_SEQ = DSL.sequence("ORDERS_SEQ")!!
        val ORDERS = DSL.table("ORDERS")!!
        val ORDERS_ID = DSL.field("ID", SQLDataType.BIGINT)!!
        val ORDERS_CODE = DSL.field("CODE", SQLDataType.VARCHAR)!!
        val ORDERS_PHONE_NUMBER = DSL.field("PHONE_NUMBER", SQLDataType.VARCHAR)!!
        val ORDERS_ORDER_STATUS = DSL.field("ORDER_STATUS", SQLDataType.VARCHAR)!!
        val ORDERS_CREATED_DATE = DSL.field("CREATED_DATE", SQLDataType.DATE.asConvertedDataType(DateConverter()))!!
        val FULL_NAME = DSL.field("FULL_NAME", SQLDataType.VARCHAR)!!
        val REGION_SOATO = DSL.field("REGION_SOATO", SQLDataType.VARCHAR)!!
        val AREA_SOATO = DSL.field("AREA_SOATO", SQLDataType.VARCHAR)!!

        val ORDER_ITEM_SEQ = DSL.sequence("ORDER_ITEM_SEQ")!!
        val ORDER_ITEM = DSL.table("ORDER_ITEM")!!
        val ORDER_ITEM_ID = DSL.field("ID", SQLDataType.BIGINT)!!
        val ORDER_ITEM_ORDER_ID = DSL.field("ORDER_ID", SQLDataType.BIGINT)!!
        val ORDER_ITEM_PRODUCT_ID = DSL.field("PRODUCT_ID", SQLDataType.BIGINT)!!
        val ORDER_ITEM_COUNT = DSL.field("COUNT", SQLDataType.DOUBLE)!!
        val ORDER_ITEM_PRICE = DSL.field("PRICE", SQLDataType.DOUBLE)!!

        val ORDER_PAYMENT_SEQ = DSL.sequence("ORDER_PAYMENT_SEQ")!!
        val ORDER_PAYMENT = DSL.table("ORDER_PAYMENT")!!
        val ORDER_PAYMENT_ID = DSL.field("ID", SQLDataType.BIGINT)!!
        val ORDER_PAYMENT_ORDER_ID = DSL.field("ORDER_ID", SQLDataType.BIGINT)!!
        val ORDER_PAYMENT_TYPE = DSL.field("PAYMENT_TYPE", SQLDataType.VARCHAR)!!
        val ORDER_PAYMENT_DETAILS = DSL.field("PAYMENT_DETAILS", SQLDataType.VARCHAR)!!
        val ORDER_PAYMENT_TOTAL_PRICE = DSL.field("TOTAL_PRICE", SQLDataType.DOUBLE)!!
        val ORDER_PAYMENT_FIRST_PAY = DSL.field("FIRST_PAY", SQLDataType.DOUBLE)!!
        val ORDER_PAYMENT_CREATED_DATE = DSL.field("CREATED_DATE", SQLDataType.DATE.asConvertedDataType(DateConverter()))!!


    }

}
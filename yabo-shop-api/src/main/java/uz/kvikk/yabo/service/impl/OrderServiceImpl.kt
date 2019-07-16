package uz.kvikk.yabo.service.impl

import org.hashids.Hashids
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uz.kvikk.yabo.model.enums.OrderStatus
import uz.kvikk.yabo.model.model.Product
import uz.kvikk.yabo.model.transport.OrderRequest
import uz.kvikk.yabo.model.transport.OrderResponse
import uz.kvikk.yabo.service.BotService
import uz.kvikk.yabo.service.OrderService
import uz.kvikk.yabo.utils.converter.DateConverter
import java.util.*

@Service
class OrderServiceImpl(val hashids: Hashids, val dsl: DSLContext, val botService: BotService) : OrderService {

    @Transactional
    override fun create(orderRequest: OrderRequest): OrderResponse {
        val orderId = dsl.select(ORDERS_SEQ.nextval()).fetchOne().into(Long::class.java)
        dsl.insertInto(ORDERS,
                ORDERS_ID,
                ORDERS_CODE,
                ORDERS_PHONE_NUMBER,
//                ORDERS_EMAIL,
//                ORDERS_FIRST_NAME,
//                ORDERS_LAST_NAME,
                ORDERS_ORDER_STATUS,
                ORDERS_CREATED_DATE
        ).values(
                orderId,
                hashids.encode(orderId),
                orderRequest.phoneNumber,
//                orderRequest.email,
//                orderRequest.firstName,
//                orderRequest.lastName,
                OrderStatus.NEW.name,
                Date()
        ).execute()

        for (item in orderRequest.orderItems) {
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
                    orderId,
                    product.id,
                    item.count,
                    product.price
            ).execute()
        }

        var message = """
            This is a test content
            Region: ${orderRequest.regionSoato}
            Area: ${orderRequest.areaSoato}
            Tel: ${orderRequest.phoneNumber}
            FIO: ${orderRequest.fullName}
            paymentType: ${orderRequest.paymentType}
            installmentPlan: ${orderRequest.installmentPlan}
            payerPassportBack: ${orderRequest.payerPassportBack}
            payerPassportFront: ${orderRequest.payerPassportFront}
            payerSalaryReport: ${orderRequest.payerSalaryReport}
            guarantorPassportBack: ${orderRequest.guarantorPassportBack}
            guarantorPassportFront: ${orderRequest.guarantorPassportFront}
            guarantorSalaryReport: ${orderRequest.guarantorSalaryReport}
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
        val ORDERS_EMAIL = DSL.field("EMAIL", SQLDataType.VARCHAR)!!
        val ORDERS_FIRST_NAME = DSL.field("FIRST_NAME", SQLDataType.VARCHAR)!!
        val ORDERS_LAST_NAME = DSL.field("LAST_NAME", SQLDataType.VARCHAR)!!
        val ORDERS_ORDER_STATUS = DSL.field("ORDER_STATUS", SQLDataType.VARCHAR)!!
        val ORDERS_MESSAGE = DSL.field("MESSAGE", SQLDataType.VARCHAR)!!
        val ORDERS_CREATED_DATE = DSL.field("CREATED_DATE", SQLDataType.DATE.asConvertedDataType(DateConverter()))!!

        val ORDER_ITEM_SEQ = DSL.sequence("ORDER_ITEM_SEQ")!!
        val ORDER_ITEM = DSL.table("ORDER_ITEM")!!
        val ORDER_ITEM_ID = DSL.field("ID", SQLDataType.BIGINT)!!
        val ORDER_ITEM_ORDER_ID = DSL.field("ORDER_ID", SQLDataType.BIGINT)!!
        val ORDER_ITEM_PRODUCT_ID = DSL.field("PRODUCT_ID", SQLDataType.BIGINT)!!
        val ORDER_ITEM_COUNT = DSL.field("COUNT", SQLDataType.DOUBLE)!!
        val ORDER_ITEM_PRICE = DSL.field("PRICE", SQLDataType.DOUBLE)!!


    }

}
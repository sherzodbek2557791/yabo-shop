package uz.kvikk.yabo.service.impl

import org.hashids.Hashids
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import org.json.XMLTokener.entity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import uz.kvikk.yabo.model.enums.OrderStatus
import uz.kvikk.yabo.model.transport.OrderRequest
import uz.kvikk.yabo.model.transport.OrderResponse
import uz.kvikk.yabo.model.transport.ProductResponse
import uz.kvikk.yabo.service.BotService
import uz.kvikk.yabo.service.OrderService
import uz.kvikk.yabo.service.ProductService
import uz.kvikk.yabo.utils.TripleFunction
import uz.kvikk.yabo.utils.converter.DateConverter
import java.util.*

@Service
class OrderServiceImpl(val hashids: Hashids, val dsl: DSLContext, val botService: BotService) : OrderService {

    override fun create(orderRequest: OrderRequest): OrderResponse {
        val id = dsl.select(ORDERS_SEQ.nextval()).fetchOne().into(Long::class.java)
        dsl.insertInto(ORDERS,
                ORDERS_ID,
                ORDERS_CODE,
                ORDERS_PHONE_NUMBER,
                ORDERS_EMAIL,
                ORDERS_FIRST_NAME,
                ORDERS_LAST_NAME,
                ORDERS_ORDER_STATUS,
                ORDERS_MESSAGE,
                ORDERS_CREATED_DATE
        ).values(
                id,
                hashids.encode(id),
                orderRequest.phoneNumber,
                orderRequest.email,
                orderRequest.firstName,
                orderRequest.lastName,
                OrderStatus.NEW.name,
                orderRequest.message,
                Date()
        ).execute()

        var message = """
            Tel: ${orderRequest.phoneNumber}
            e-mail: ${orderRequest.email}
            FIO: ${orderRequest.firstName} ${orderRequest.lastName}
            Message: ${orderRequest.message}
        """.trimIndent()

        for ((index, item) in orderRequest.orderItems.withIndex()){
            message += "\n" + """
                ${index+1}: ${item.code}-${item.count}x${item.price}=${item.count*item.price}
                    ${item.title}
            """.trimIndent()
        }
        botService.sendOrder(message)

        return OrderResponse(
                orderRequest.phoneNumber,
                orderRequest.email,
                orderRequest.firstName,
                orderRequest.lastName,
                orderRequest.message,
                orderRequest.orderItems
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

        val ORDER_ITEM = DSL.table("ORDER_ITEM")!!

    }

}
package uz.kvikk.yabo.service.impl

import org.jooq.DSLContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import uz.kvikk.yabo.model.transport.OrderRequest
import uz.kvikk.yabo.model.transport.OrderResponse
import uz.kvikk.yabo.model.transport.ProductResponse
import uz.kvikk.yabo.service.BotService
import uz.kvikk.yabo.service.OrderService
import uz.kvikk.yabo.service.ProductService
import uz.kvikk.yabo.utils.TripleFunction

@Service
class OrderServiceImpl(val dsl: DSLContext, val botService: BotService) : OrderService {


    override fun create(orderRequest: OrderRequest): OrderResponse {

        botService.sendOrder("""
            Tel: ${orderRequest.phoneNumber}
            e-mail: ${orderRequest.email}
            FIO: ${orderRequest.firstName} ${orderRequest.lastName}
            Message: ${orderRequest.message}
        """.trimIndent())

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
        var SELECT: String = """
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

        var TOTAL: String = """
            select count(p.id)
            from product p
            where 1=1
        """.trimIndent()
    }

}
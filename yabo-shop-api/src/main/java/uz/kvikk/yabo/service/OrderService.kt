package uz.kvikk.yabo.service

import uz.kvikk.yabo.model.transport.OrderDTO
import uz.kvikk.yabo.model.transport.OrderRequest
import uz.kvikk.yabo.model.transport.OrderResponse

interface OrderService {

    fun create(orderRequest: OrderRequest): OrderResponse

    fun oneOrder(code:String): OrderDTO

}
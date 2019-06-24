package uz.kvikk.yabo.model.transport

data class OrderItemRequest(

        val productCode: String,
        val count: Double,
        val price: Double

)

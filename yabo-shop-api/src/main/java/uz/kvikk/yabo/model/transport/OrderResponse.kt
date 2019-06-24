package uz.kvikk.yabo.model.transport

data class OrderResponse(

        val phoneNumber: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val message: String?,
        val orderItems: List<OrderItemRequest>
)

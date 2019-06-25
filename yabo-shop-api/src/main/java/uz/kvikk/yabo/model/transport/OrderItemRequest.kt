package uz.kvikk.yabo.model.transport

data class OrderItemRequest(

        val code: String,
        val count: Double,
        val price: Double,
        val currency: String,
        val description: String?,
        val image: String,
        val title: String,
        val start_date: String

)

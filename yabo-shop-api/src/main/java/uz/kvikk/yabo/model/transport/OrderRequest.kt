package uz.kvikk.yabo.model.transport

data class OrderRequest(

        val orderItems: List<OrderItemRequest>,

        val regionSoato: String,
        val areaSoato: String,
        val fullName: String,
        val phoneNumber: String,
        val paymentType: String,

        /**
         * Here starts the details of order for payment
         */
        val installmentPlan: String?,
        val payerPassportBack: String?,
        val payerPassportFront: String?,
        val payerSalaryReport: String?,
        val guarantorPassportBack: String?,
        val guarantorPassportFront: String?,
        val guarantorSalaryReport: String?,
        val message: String?

)

package uz.kvikk.yabo.model.transport

import javax.persistence.Column

data class OrderDTO(
        @Column(name = "ID")
        val id: Long,

        @Column(name = "CODE")
        val code: String,

        @Column(name = "REGION_SOATO")
        val regionSoato: String,

        @Column(name = "REGION_NAME_UZ_UZ")
        val regionNameUzUz: String,

        @Column(name = "REGION_NAME_RU_RU")
        val regionNameRuRu: String,

        @Column(name = "AREA_SOATO")
        val areaSoato: String,

        @Column(name = "AREA_NAME_UZ_UZ")
        val areaNameUzUz: String,

        @Column(name = "AREA_NAME_RU_RU")
        val areaNameRuRu: String,

        @Column(name = "FULL_NAME")
        val fullName: String,

        @Column(name = "PHONE_NUMBER")
        val phoneNumber: String,

        @Column(name = "PAYMENT_TYPE")
        val paymentType: String?
)

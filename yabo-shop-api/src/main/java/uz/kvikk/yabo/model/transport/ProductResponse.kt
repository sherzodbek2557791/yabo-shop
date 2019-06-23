package uz.kvikk.yabo.model.transport

import javax.persistence.Column

data class ProductResponse(

        @Column(name = "CODE")
        val code: String,

        @Column(name = "TITLE")
        val title: String,

        @Column(name = "DESCRIPTION")
        val description: String,

        @Column(name = "PRICE")
        val price: Double,

        @Column(name = "CURRENCY")
        val currency: String,

        @Column(name = "IMAGE")
        val image: String,

        @Column(name = "START_DATE")
        val start_date: String?

)

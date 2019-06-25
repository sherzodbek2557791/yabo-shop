package uz.kvikk.yabo.model.model

import java.util.*
import javax.persistence.Column

data class Product(
        @Column(name = "ID")
        val id: Long,

        @Column(name = "CODE")
        val code: String,

        @Column(name = "TITLE")
        val title: String,

        @Column(name = "DESCRIPTION")
        val description: String?,

        @Column(name = "PRODUCT_STATUS")
        val product_status: String,

        @Column(name = "PRICE")
        val price: Double,

        @Column(name = "CURRENCY")
        val currency: String,

        @Column(name = "IMAGE")
        val image: String,

        @Column(name = "START_DATE")
        val start_date: Date
)

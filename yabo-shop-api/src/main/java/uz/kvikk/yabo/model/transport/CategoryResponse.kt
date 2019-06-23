package uz.kvikk.yabo.model.transport

import javax.persistence.Column

data class CategoryResponse(

        @Column(name = "CODE")
        val code: String,

        @Column(name = "TITLE")
        val title: String,

        @Column(name = "DESCRIPTION")
        val description: String?,

        @Column(name = "COLOR")
        val color: String,

        @Column(name = "HEADER_IMG")
        val headerImg: String

)

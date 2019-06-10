package uz.kvikk.yabo.model.transport

import javax.persistence.Column

data class GroupResponse(

        @Column(name = "ID")
        val id: Long,

        @Column(name = "PARENT_ID")
        val parentId: Long?,

        @Column(name = "TITLE")
        val title: String,

        @Column(name = "DESCRIPTION")
        val description: String?

)

package uz.kvikk.yabo.model.transport

import javax.persistence.Column

data class RegionResponse(

        @Column(name = "SOATO")
        val soato: String,

        @Column(name = "NAME_UZ_UZ")
        val nameUzUz: String?,

        @Column(name = "NAME_RU_RU")
        val nameRuRu: String?,

        @Column(name = "CENTER_REGION_NAME_UZ_UZ")
        val centerRegionNameUzUz: String?,

        @Column(name = "CENTER_REGION_NAME_RU_RU")
        val centerRegionNameRuRu: String?
)


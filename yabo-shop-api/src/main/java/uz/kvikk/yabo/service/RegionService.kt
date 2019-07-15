package uz.kvikk.yabo.service

import uz.kvikk.yabo.model.transport.RegionResponse

interface RegionService {

    fun listRegion(): List<RegionResponse>

    fun listArea(soato: String): List<RegionResponse>

}
package uz.kvikk.yabo.service.impl

import org.jooq.DSLContext
import org.springframework.stereotype.Service
import uz.kvikk.yabo.model.transport.RegionResponse
import uz.kvikk.yabo.service.RegionService

@Service
class RegionServiceImpl(val dsl: DSLContext) : RegionService {


    override fun listRegion(): List<RegionResponse> {
        return dsl.fetch("""
            select *
            from region r
            where length(r.soato)=4
        """.trimIndent()).into(RegionResponse::class.java)
    }

    override fun listArea(soato: String): List<RegionResponse> {
        return dsl.fetch("""
            select *
            from region r
            where length(r.soato)=7 
              and substr(r.soato, 1, 4)={0}
        """.trimIndent(), soato).into(RegionResponse::class.java)
    }

}
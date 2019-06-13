package uz.kvikk.yabo.service.impl

import org.jooq.DSLContext
import org.springframework.stereotype.Service
import uz.kvikk.yabo.model.transport.ProductListResponse
import uz.kvikk.yabo.service.ProductService

@Service
class ProductServiceImpl(val dsl: DSLContext) : ProductService {


    override fun list(): List<ProductListResponse> {
        return dsl.fetch("""
            select
               g.id,
               g.parent_id,
               g.title,
               g.description
            from O_GROUP g
            where g.parent_id is null
        """.trimIndent()).into(ProductListResponse::class.java)
    }

}
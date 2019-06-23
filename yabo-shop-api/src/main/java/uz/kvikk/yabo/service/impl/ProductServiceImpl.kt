package uz.kvikk.yabo.service.impl

import org.jooq.DSLContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import uz.kvikk.yabo.model.transport.ProductListResponse
import uz.kvikk.yabo.model.transport.ProductResponse
import uz.kvikk.yabo.service.ProductService
import java.util.*

@Service
class ProductServiceImpl(val dsl: DSLContext) : ProductService {
    override fun listPagingFiltered(pageable: Pageable, productStatus: String, filters: Map<String, Any>): Page<ProductResponse> {
        var params = Collections.emptyList<Any>()
        var select = "$SELECT and p.product_status = {0} "
        var i = 1
        filters.forEach { (k, v) ->
            select += """
                and p.$k = {${i++}}
            """.trimIndent()
            params.add(v)
        }

        select += " OFFSET {${i++}} ROWS FETCH NEXT {${i++}} ROWS ONLY "
        params.add(pageable.offset)
        params.add(pageable.pageSize)

        val list = dsl.fetch(select, params).into(ProductResponse::class.java)

        val total = dsl.fetchOne("""
            $TOTAL
            and p.product_status = {0}
            OFFSET {1} ROWS FETCH NEXT {2} ROWS ONLY 
        """.trimMargin(), productStatus).into(Long::class.java)
        return PageImpl(list, pageable, total)
    }

    override fun listPaging(pageable: Pageable, productStatus: String): Page<ProductResponse> {
        val list = dsl.fetch("""
            $SELECT
            and p.product_status = {0}
            OFFSET {1} ROWS FETCH NEXT {2} ROWS ONLY 
        """.trimMargin(),
                productStatus,
                pageable.offset,
                pageable.pageSize
        ).into(ProductResponse::class.java)

        val total = dsl.fetchOne("""
            $TOTAL
            and p.product_status = {0}
        """.trimMargin(), productStatus).into(Long::class.java)
        return PageImpl(list, pageable, total)
    }


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

    companion object {
        var SELECT: String = """
           select p.code,
                   p.title,
                   p.description,
                   p.price,
                   p.currency,
                   p.image,
                   p.start_date
            from product p
            where 1=1
        """.trimIndent()

        var TOTAL: String = """
            select count(p.id)
            from product p
            where 1=1
        """.trimIndent()
    }

}
package uz.kvikk.yabo.service.impl

import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Service
import uz.kvikk.yabo.model.transport.CategoryResponse
import uz.kvikk.yabo.service.CategoryService

@Service
class CategoryServiceImpl(val dsl: DSLContext) : CategoryService {


    override fun one(code: String, categoryStatus: String): CategoryResponse {
        val record: Record? = dsl.fetchOne("""
            select c.code,
                   c.title,
                   c.description,
                   c.color,
                   c.header_img
            from category c
            where c.category_status = {0} and c.code = {1}
        """.trimIndent(), categoryStatus, code)

        return record!!.into(CategoryResponse::class.java)
    }

    override fun list(categoryStatus: String): List<CategoryResponse> {
        return dsl.fetch("""
            select c.code,
                   c.title,
                   c.description,
                   c.color,
                   c.header_img
            from category c
            where c.category_status = {0}
        """.trimIndent(), categoryStatus).into(CategoryResponse::class.java)
    }

}
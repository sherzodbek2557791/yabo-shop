package uz.kvikk.yabo.service.impl

import org.jooq.DSLContext
import org.springframework.stereotype.Service
import uz.kvikk.yabo.model.transport.CategoryResponse
import uz.kvikk.yabo.service.CategoryService

@Service
class CategoryServiceImpl(val dsl: DSLContext) : CategoryService {

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
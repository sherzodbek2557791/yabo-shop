package uz.kvikk.yabo.service.impl

import org.jooq.DSLContext
import org.springframework.stereotype.Service
import uz.kvikk.yabo.model.transport.GroupResponse
import uz.kvikk.yabo.service.CategoryGroupService

@Service
class CategoryGroupServiceImpl(val dsl: DSLContext) : CategoryGroupService {


    override fun listTree(parentId: Long): List<GroupResponse> {
        return dsl.fetch("""
            select
               g.id,
               g.parent_id,
               g.title,
               g.description
            from O_GROUP g
            where g.parent_id is null
        """.trimIndent()).into(GroupResponse::class.java)
    }

}
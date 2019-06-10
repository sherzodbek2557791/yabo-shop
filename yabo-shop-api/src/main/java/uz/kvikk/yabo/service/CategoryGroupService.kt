package uz.kvikk.yabo.service

import uz.kvikk.yabo.model.transport.GroupResponse

interface CategoryGroupService {

    fun listTree(parentId: Long): List<GroupResponse>
}
package uz.kvikk.yabo.service

import uz.kvikk.yabo.model.transport.GroupResponce

interface CategoryGroupService {

    fun listTree(): List<GroupResponce>
}
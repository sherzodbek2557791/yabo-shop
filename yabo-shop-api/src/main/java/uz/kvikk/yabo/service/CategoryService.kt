package uz.kvikk.yabo.service

import uz.kvikk.yabo.model.transport.CategoryResponse

interface CategoryService {

    fun list(categoryStatus: String): List<CategoryResponse>
}
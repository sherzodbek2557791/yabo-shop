package uz.kvikk.yabo.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import uz.kvikk.yabo.model.transport.ProductListResponse
import uz.kvikk.yabo.model.transport.ProductResponse

interface ProductService {

    fun list(): List<ProductListResponse>

    fun listPaging(pageable: Pageable, productStatus: String) :Page<ProductResponse>

    fun listPagingFiltered(pageable: Pageable, productStatus: String, filters: Map<String, Any>) :Page<ProductResponse>

}
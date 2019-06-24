package uz.kvikk.yabo.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import uz.kvikk.yabo.model.transport.ProductListResponse
import uz.kvikk.yabo.model.transport.ProductResponse
import uz.kvikk.yabo.utils.TripleFunction
import java.util.function.BiFunction

interface ProductService {

    fun listPaging(pageable: Pageable, productStatus: String) :Page<ProductResponse>

    fun listPagingFiltered(pageable: Pageable, productStatus: String, categoryCode: String?) :Page<ProductResponse>

    fun pageFiltered(pageable: Pageable, filters: Map<TripleFunction<MutableList<Any>, MutableList<Any>, Int, String>, Any>): Page<ProductResponse>

}
package uz.kvikk.yabo.service

import uz.kvikk.yabo.model.transport.ProductListResponse

interface ProductService {

    fun list(): List<ProductListResponse>
}
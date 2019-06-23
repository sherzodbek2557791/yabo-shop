package uz.kvikk.yabo.controller

import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uz.kvikk.yabo.service.ProductService

@RestController
@RequestMapping("/api/product")
class ProductController(val productService: ProductService) {


    @GetMapping("/list-paging")
    fun listPaging(pageable: Pageable): ResponseEntity<Any> = ResponseEntity.ok(productService.listPaging(pageable, "ACTIVE"))

    @GetMapping("/list-paging-filtered")
    fun listPagingFiltered(pageable: Pageable,
                           @RequestParam(required = false) category: String): ResponseEntity<Any> =
            ResponseEntity.ok(productService.listPaging(pageable, "ACTIVE"))

}
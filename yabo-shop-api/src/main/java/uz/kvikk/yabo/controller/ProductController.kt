package uz.kvikk.yabo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uz.kvikk.yabo.service.CategoryGroupService

@RestController
@RequestMapping("/api/product")
class ProductController(val categoryGroupService: CategoryGroupService) {

    @GetMapping("/list")
    fun listTree(): ResponseEntity<Any> = ResponseEntity.ok(categoryGroupService.listTree(0))

}
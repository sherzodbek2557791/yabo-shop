package uz.kvikk.yabo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uz.kvikk.yabo.service.CategoryService

@RestController
@RequestMapping("/api/category")
class CategoryController(val categoryService: CategoryService) {

    @GetMapping("/list")
    fun list(): ResponseEntity<Any> = ResponseEntity.ok(categoryService.list("ACTIVE"))

}
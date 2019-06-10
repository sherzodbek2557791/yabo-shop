package uz.kvikk.yabo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/category_group")
class CategoryGroupController {

    @GetMapping("/list-tree")
    fun listTree(): ResponseEntity<Any> = ResponseEntity.ok(mapOf("test" to "test"))

}
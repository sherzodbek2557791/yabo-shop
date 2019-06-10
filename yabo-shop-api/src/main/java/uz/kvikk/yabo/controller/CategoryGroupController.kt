package uz.kvikk.yabo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uz.kvikk.yabo.service.CategoryGroupService

@RestController
@RequestMapping("/api/category_group")
class CategoryGroupController(val categoryGroupService: CategoryGroupService) {

    @GetMapping("/list-tree")
    fun listTree(): ResponseEntity<Any> = ResponseEntity.ok(categoryGroupService.listTree(0))

}
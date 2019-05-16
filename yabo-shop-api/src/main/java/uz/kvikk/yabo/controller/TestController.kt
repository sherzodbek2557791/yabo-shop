package uz.kvikk.yabo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping("/test")
    fun test(): ResponseEntity<Any> = ResponseEntity.ok(mapOf("test" to "test"))

}
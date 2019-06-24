package uz.kvikk.yabo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uz.kvikk.yabo.model.transport.OrderRequest
import uz.kvikk.yabo.service.OrderService

@RestController
@RequestMapping("/api/order")
class OrderController(val orderService: OrderService) {


    @PostMapping("/create")
    fun create(@RequestBody orderRequest: OrderRequest): ResponseEntity<Any> = ResponseEntity.ok(orderService.create(orderRequest))

}
package uz.kvikk.yabo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uz.kvikk.yabo.service.RegionService

@RestController
@RequestMapping("/api/region")
class RegionController(val regionService: RegionService) {

    @GetMapping("/list-region")
    fun listRegion(): ResponseEntity<Any> = ResponseEntity.ok(regionService.listRegion())

    @GetMapping("/list-area")
    fun listArea(@RequestParam parentSoato: String): ResponseEntity<Any> = ResponseEntity.ok(regionService.listArea(parentSoato))

}
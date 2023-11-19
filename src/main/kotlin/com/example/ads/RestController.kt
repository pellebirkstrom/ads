package com.example.ads

import jakarta.validation.Valid
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Component
@RestController
@ResponseBody
class RestController(
    private val adService: AdService,
) {

    @PostMapping("/ads")
    fun createAd(
        @Valid
        @RequestBody
        request: CreateAdRequest,
    ): Ad = adService.createAd(
        subject = request.subject,
        body = request.body,
        price = request.price,
        email = request.email,
    )

    @GetMapping("/ads")
    fun getAllAds(): List<Ad> = adService.getAllAds()

    @GetMapping("/ads/{id}")
    fun getAdById(@PathVariable id: UUID): Ad =
        adService.getAdById(id) ?: throw ResponseStatusException(NOT_FOUND)

    @DeleteMapping("/ads/{id}")
    fun deleteAdById(@PathVariable id: UUID): Ad =
        adService.deleteAdById(id) ?: throw ResponseStatusException(NOT_FOUND)

    // TODO: Add tests - DONE
    // TODO: Add pagination and sort
    // TODO: Add Docker packaging
    // TODO: Maybe propate exception message to the client
    // TODO: Maybe add a unit test for sake of conversation?
}

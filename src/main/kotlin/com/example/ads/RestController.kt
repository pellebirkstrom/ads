package com.example.ads

import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
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
    @Transactional
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
    @Transactional
    fun getAds(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "createdAt") sortBy: String,
        @RequestParam(required = false, defaultValue = "desc") direction: String,
    ): Page<Ad> {
        val sortDirection = when (direction.uppercase()) {
            "ASC" -> ASC
            "DESC" -> DESC
            else -> throw IllegalArgumentException("Invalid sort direction: $direction")
        }
        val sortField = when (sortBy.uppercase()) {
            "CREATEDAT" -> "createdAt"
            "PRICE" -> "price"
            else -> throw IllegalArgumentException("Invalid sort field: $sortBy")
        }
        val pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField))
        return adService.getAds(pageable)
    }

    @GetMapping("/ads/{id}")
    @Transactional
    fun getAdById(@PathVariable id: UUID): Ad =
        adService.getAdById(id) ?: throw ResponseStatusException(NOT_FOUND)

    @DeleteMapping("/ads/{id}")
    @Transactional
    fun deleteAdById(@PathVariable id: UUID): Ad =
        adService.deleteAdById(id) ?: throw ResponseStatusException(NOT_FOUND)
}

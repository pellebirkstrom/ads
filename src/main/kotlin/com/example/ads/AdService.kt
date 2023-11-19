package com.example.ads

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service
class AdService(private val adRepository: AdRepository) {

    @Transactional
    fun createAd(
        subject: String,
        body: String,
        price: Int?,
        email: String,
    ): Ad = adRepository.save(
        Ad(
            id = UUID.randomUUID(),
            subject = subject,
            body = body,
            price = price,
            email = email,
            createdAt = Instant.now(),
        ),
    )

    @Transactional
    fun getAdById(id: UUID): Ad? = adRepository.findByIdOrNull(id)

    @Transactional
    fun getAds(pageable: Pageable): Page<Ad> = adRepository.findAll(pageable)

    @Transactional
    fun deleteAdById(id: UUID): Ad? =
        adRepository.findByIdOrNull(id)?.also {
            adRepository.delete(it)
        }
}

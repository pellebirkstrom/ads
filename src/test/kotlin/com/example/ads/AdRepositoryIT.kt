package com.example.ads

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.time.Instant
import java.util.UUID.randomUUID

@IntegrationTest
class AdRepositoryIT(private val adRepository: AdRepository) {

    @Test
    fun `should read and write ad entity`() {
        val id = randomUUID()
        val createdAt = Instant.now()
        adRepository.save(
            Ad(
                id = id,
                subject = "subject",
                body = "body",
                price = 100,
                email = "email",
                createdAt = createdAt,
            ),
        )
        val ad = adRepository.findByIdOrNull(id)!!
        assertThat(ad.subject).isEqualTo("subject")
        assertThat(ad.body).isEqualTo("body")
        assertThat(ad.price).isEqualTo(100)
        assertThat(ad.email).isEqualTo("email")
        assertThat(ad.createdAt).isEqualTo(createdAt)
    }
}

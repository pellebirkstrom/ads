package com.example.ads

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID.randomUUID

@IntegrationTest
class DummyRepositoryIT {

    @Autowired
    private lateinit var dummyRepository: DummyRepository

    @Test
    fun `should read-write dummy entity`() {
        val id = randomUUID()
        dummyRepository.save(DummyEntity(id))
        assertThat(dummyRepository.findById(id)).isNotNull
    }
}

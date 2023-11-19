package com.example.ads.http

import com.example.ads.Ad
import com.example.ads.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

@IntegrationTest
class HttpDeleteAdIT(
    @LocalServerPort port: Int,
    httpClient: TestRestTemplate,
) : HttpIT(port, httpClient) {

    @Test
    fun `should return Http NOT_FOUND for a delete command on unknown ad`() {
        val randomId = randomId()

        val response = httpClient.exchange(
            "http://localhost:$port/ads/{id}",
            HttpMethod.DELETE,
            HttpEntity<Any>(null, null),
            String::class.java,
            randomId,
        )

        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `should delete an ad by id and return the ad`() {
        val createdAdId = httpClient.postForEntity("http://localhost:$port/ads", createAdRequestBody(), Ad::class.java).body!!.id

        val response = httpClient.exchange(
            "http://localhost:$port/ads/{id}",
            HttpMethod.DELETE,
            HttpEntity<Any>(null, null),
            Ad::class.java,
            createdAdId,
        )

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body?.id).isEqualTo(createdAdId)

        val emptyResponse = httpClient.getForEntity("http://localhost:$port/ads/$createdAdId", String::class.java)
        assertThat(emptyResponse.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}

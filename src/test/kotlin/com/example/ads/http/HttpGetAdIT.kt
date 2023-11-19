package com.example.ads.http

import com.example.ads.Ad
import com.example.ads.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@IntegrationTest
class HttpGetAdIT(
    @LocalServerPort port: Int,
    httpClient: TestRestTemplate,
) : HttpIT(port, httpClient) {

    @Test
    fun `should return Http NOT_FOUND for an unknown ad`() {
        val response = httpClient.getForEntity("http://localhost:$port/ads/${randomId()}", String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `should return an ad fetched by id`() {
        val requestBody = createAdRequestBody()
        val createdAdId = httpClient.postForEntity("http://localhost:$port/ads", requestBody, Ad::class.java).body!!.id

        val response = getAd(createdAdId)!!

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body?.id).isNotNull
        assertThat(response.body?.subject).isEqualTo(requestBody.subject)
        assertThat(response.body?.body).isEqualTo(requestBody.body)
        assertThat(response.body?.price).isEqualTo(requestBody.price)
        assertThat(response.body?.email).isEqualTo(requestBody.email)
    }
}

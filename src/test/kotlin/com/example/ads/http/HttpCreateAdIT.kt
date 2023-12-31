package com.example.ads.http

import com.example.ads.Ad
import com.example.ads.CreateAdRequest
import com.example.ads.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@IntegrationTest
class HttpCreateAdIT(
    @LocalServerPort port: Int,
    httpClient: TestRestTemplate,
) : HttpIT(port, httpClient) {

    @Test
    fun `should create an ad with all input fields set`() {
        val requestBody = CreateAdRequest(
            subject = "subject",
            body = "body",
            price = 100,
            email = "example@bar.com",
        )

        val response = httpClient.postForEntity("http://localhost:$port/ads", requestBody, Ad::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body?.id).isNotNull
        assertThat(response.body?.subject).isEqualTo(requestBody.subject)
        assertThat(response.body?.body).isEqualTo(requestBody.body)
        assertThat(response.body?.price).isEqualTo(requestBody.price)
        assertThat(response.body?.email).isEqualTo(requestBody.email)
    }

    @Test
    fun `should create an ad with all input fields set except price`() {
        val requestBody = createAdRequestBody(price = null)

        val response = httpClient.postForEntity("http://localhost:$port/ads", requestBody, Ad::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body?.price).isNull()

        assertThat(response.body?.id).isNotNull
        assertThat(response.body?.subject).isEqualTo(requestBody.subject)
        assertThat(response.body?.body).isEqualTo(requestBody.body)
        assertThat(response.body?.email).isEqualTo(requestBody.email)
    }

    @Test
    fun `should fail creating an ad without a subject`() {
        val invalidRequestBody = mapOf(
            // "subject" to "subject", <== missing
            "body" to "subject",
            "price" to 100,
            "email" to "foo@bar.com",
        )

        val response = httpClient.postForEntity("http://localhost:$port/ads", invalidRequestBody, String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `should fail creating an ad without a body`() {
        val invalidRequestBody = mapOf(
            "subject" to "subject",
            // "body" to "body", <== missing
            "price" to 100,
            "email" to "foo@bar.com",
        )

        val response = httpClient.postForEntity("http://localhost:$port/ads", invalidRequestBody, String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `should fail creating an ad without an email`() {
        val invalidRequestBody = mapOf(
            "subject" to "subject",
            "body" to "body",
            "price" to 100,
            // "email" to "foo@bar.com" <== missing
        )

        val response = httpClient.postForEntity("http://localhost:$port/ads", invalidRequestBody, String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `should fail creating an ad with an invalid email`() {
        val requestBody = createAdRequestBody(email = "this is not a email address")

        val response = httpClient.postForEntity("http://localhost:$port/ads", requestBody, String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `should create an ad with price of Int MAX_VALUE`() {
        val requestBody = createAdRequestBody(price = Int.MAX_VALUE)

        val response = httpClient.postForEntity("http://localhost:$port/ads", requestBody, Ad::class.java)

        assertThat(response.body?.price).isEqualTo(Int.MAX_VALUE)
    }
}

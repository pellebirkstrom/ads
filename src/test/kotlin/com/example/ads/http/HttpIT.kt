package com.example.ads.http

import com.example.ads.Ad
import com.example.ads.CreateAdRequest
import com.example.ads.IntegrationTest
import kotlin.random.Random
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.ResponseEntity
import java.util.UUID

@IntegrationTest
class HttpIT(
    @LocalServerPort protected val port: Int,
    protected val httpClient: TestRestTemplate,
) {
    fun createAds(count: Int): List<Ad> = (0 until count).map { createAd() }

    fun createAd(price: Int? = null): Ad =
        httpClient.postForEntity("http://localhost:$port/ads", createAdRequestBody(price), Ad::class.java).body!!

    fun createAdRequestBody(price: Int? = Random.nextInt(), email: String? = null): CreateAdRequest =
        CreateAdRequest(
            subject = "subject ${randomString()}",
            body = "body ${randomString()}",
            price = price,
            email = email ?: "${randomString()}@bar.com",
        )

    fun getAd(id: UUID): ResponseEntity<Ad>? = httpClient.getForEntity("http://localhost:$port/ads/$id", Ad::class.java)

    fun randomId(): String = UUID.randomUUID().toString()
    fun randomString(): String = randomId()
}

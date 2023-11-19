package com.example.ads.http

import com.example.ads.Ad
import com.example.ads.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.UUID

@IntegrationTest
class HttpGetPaginatedListIT(
    @LocalServerPort port: Int,
    httpClient: TestRestTemplate,
) : HttpIT(port, httpClient) {
    private val responseType = object : ParameterizedTypeReference<ResponsePage>() {}

    @BeforeEach
    fun setUp() = deleteAll()

    @Test
    fun `should return paginated list of ads default ordered by newest first`() {
        createAds(10)
        val secondNewestAd = createAd(price = 200)
        val newestAd = createAd(price = 300)

        val response: ResponseEntity<ResponsePage> = httpClient.getForEntity(
            "http://localhost:$port/ads",
            responseType,
        )

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body?.content?.size).isEqualTo(10)

        assertThat(response.body?.totalElements).isEqualTo(12)
        assertThat(response.body?.numberOfElements).isEqualTo(10)
        assertThat(response.body?.last).isFalse
        assertThat(response.body?.totalPages).isEqualTo(2)

        assertThat(response.body?.content?.get(0)?.id).isEqualTo(newestAd.id)
        assertThat(response.body?.content?.get(1)?.id).isEqualTo(secondNewestAd.id)
    }

    @Test
    fun `should get ads ordered by price asc`() {
        val mediumPriceAd = createAd(price = 100)
        val lowPriceAd = createAd(price = 50)
        val highPriceAd = createAd(price = 200)

        val ads = httpClient.getForEntity<ResponsePage>(
            "http://localhost:$port/ads?sortBy=price&direction=asc",
            responseType,
        ).body!!.content

        assertThat(ads[0].id).isEqualTo(lowPriceAd.id)
        assertThat(ads[1].id).isEqualTo(mediumPriceAd.id)
        assertThat(ads[2].id).isEqualTo(highPriceAd.id)
    }

    @Test
    fun `should get ads ordered by price desc`() {
        val mediumPriceAd = createAd(price = 100)
        val lowPriceAd = createAd(price = 50)
        val highPriceAd = createAd(price = 200)

        val ads = httpClient.getForEntity<ResponsePage>(
            "http://localhost:$port/ads?sortBy=price&direction=desc",
            responseType,
        ).body!!.content

        assertThat(ads[0].id).isEqualTo(highPriceAd.id)
        assertThat(ads[1].id).isEqualTo(mediumPriceAd.id)
        assertThat(ads[2].id).isEqualTo(lowPriceAd.id)
    }

    private fun getAll(): List<Ad> =
        httpClient.getForEntity<ResponsePage>(
            "http://localhost:$port/ads?size=1000",
            responseType,
        ).body?.content ?: emptyList()

    private fun deleteAll() {
        getAll().map { ad -> delete(ad.id) }
    }

    private fun delete(id: UUID) {
        httpClient.delete("http://localhost:$port/ads/$id")
    }
}

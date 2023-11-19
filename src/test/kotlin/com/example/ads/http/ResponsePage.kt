package com.example.ads.http

import com.example.ads.Ad
import com.fasterxml.jackson.databind.JsonNode

data class ResponsePage(
    val content: List<Ad>,
    val number: Int,
    val size: Int,
    val totalElements: Long,
    val pageable: JsonNode,
    val last: Boolean,
    val totalPages: Int,
    val sort: JsonNode,
    val first: Boolean,
    val numberOfElements: Int,
)

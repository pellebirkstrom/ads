package com.example.ads

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateAdRequest(
    @field:NotBlank(message = "Subject is required")
    val subject: String,

    @field:NotBlank(message = "Body is required")
    val body: String,

    val price: Int?,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email must be valid")
    val email: String,
)

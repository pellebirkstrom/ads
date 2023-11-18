package com.example.ads

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
class Ad(
    @Id val id: UUID,
    val subject: String,
    val body: String,
    val price: Int?,
    val email: String,
)

package com.example.ads

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity(name = "dummy")
class DummyEntity(
    @Id val id: UUID,
)

package com.example.ads

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AdRepository : JpaRepository<Ad, UUID>

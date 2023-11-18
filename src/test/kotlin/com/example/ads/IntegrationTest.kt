package com.example.ads
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.flywaydb.test.annotation.FlywayTest
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestInstance(Lifecycle.PER_CLASS)
@TestExecutionListeners(
    mergeMode = MERGE_WITH_DEFAULTS,
)
@AutoConfigureEmbeddedDatabase
@FlywayTest // Each test will get a clean and migrated DB
annotation class IntegrationTest

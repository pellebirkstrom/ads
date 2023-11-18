package com.example.ads

import org.junit.jupiter.api.Test

@IntegrationTest
class AdsApplicationIT {

    @Test
    fun `Should start application without errors`() {
        // This test is here to verify that the application context can start
        // with all flyway migrations applied and the embedded database running
        // if this does not throw an exception, the application context is able to start
    }
}

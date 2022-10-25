package com.formatter.demo

import com.formatter.demo.repository.LogRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 0)
@TestPropertySource(
    properties = ["wiremock.url=http://localhost:\${wiremock.server.port}"]
)
class DemoApplicationTests(
    @Autowired val webClient: WebTestClient,
    @Autowired val logRepository: LogRepository
) {

    @Test
    fun contextLoads() {
        webClient.get().uri("/api").exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java).returnResult().responseBody.let { Assertions.assertEquals("no healthy upstream", it) }

        val logs = logRepository.findAll()
        Assertions.assertEquals(1, logs.size)
    }
}

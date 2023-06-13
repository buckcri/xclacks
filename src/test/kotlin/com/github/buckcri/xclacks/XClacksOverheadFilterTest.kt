package com.github.buckcri.xclacks

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootApplication
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ServletComponentScan
internal class XClacksOverheadFilterTest(@Autowired val webTestClient: WebTestClient) {

    @Test
    fun testHeaderAndValueExist() {
        webTestClient.get().uri("/foo").exchange().expectStatus().isNotFound().expectHeader().valueMatches("X-Clacks-Overhead", "GNU Terry Pratchett")
    }
}
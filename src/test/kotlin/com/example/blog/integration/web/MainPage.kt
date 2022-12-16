package com.example.blog.integration.web

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainPage(@Autowired val restTemplate: TestRestTemplate) {

    private val logger: Logger = LogManager.getLogger(MainPage::class.java)

    @BeforeAll
    fun setup() {
        logger.info(">> Setup")
    }

    @Test
    fun `Assert blog page title, content and status code`() {
        println(">> Assert blog page title, content and status code")
        val entity = restTemplate.getForEntity<String>("/")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains(" <h1>Welcome to my awesome new Blog!</h1>")
    }

    @Test
    fun `Assert article page title, content and status code`() {
        println(">> Assert author page, content and status code")
        val entity = restTemplate.getForEntity<String>("/author/pm1")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("pierrick", "MARIE", "A jedi!")
    }

    @AfterAll
    fun teardown() {
        logger.info(">> Tear down")
    }

}
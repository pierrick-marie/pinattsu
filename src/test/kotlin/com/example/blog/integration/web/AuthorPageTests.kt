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
class AuthorPageTests(@Autowired val restTemplate: TestRestTemplate) {

    private val logger: Logger = LogManager.getLogger(AuthorPageTests::class.java)

    @BeforeAll
    fun setup() {
        logger.info(">> Setup")
    }

    @Test
    fun `Assert author personal page, content and status code`() {
        println(">> Assert author page, content and status code")
        val entity = restTemplate.getForEntity<String>("/author/pm1")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("pierrick", "MARIE", "A jedi!")
    }

    @Test
    fun `Assert authors page, content and status code`() {
        println(">> Assert authors page, content and status code")
        val entity = restTemplate.getForEntity<String>("/authors")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("Authors", "pierrick", "peter")
    }

    @AfterAll
    fun teardown() {
        logger.info(">> Tear down")
    }

}
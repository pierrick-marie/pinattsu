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
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class AuthorPageTests(@Autowired val restTemplate: TestRestTemplate) {

    private val logger: Logger = LogManager.getLogger(AuthorPageTests::class.java)

    @BeforeAll
    fun setup() {
        logger.info(">> Setup")
    }

    @Test
    fun `Assert author personal page, content and status code`() {
        logger.info(">> Assert author page, content and status code")
        val entity = restTemplate.getForEntity<String>("/author/ae")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("<title>Annie</title>", "<h2>Annie EASLEY</h2>", """<p>She is well known for being one of the famous women in technology for encouraging women and people of her colour to study and enter STEM fields.</p>""")
    }

    @Test
    fun `Assert authors page, content and status code`() {
        logger.info(">> Assert authors page, content and status code")
        val entity = restTemplate.getForEntity<String>("/authors")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("<title>Authors</title>", "<h2>Annie EASLEY</h2>", "<h2>Mary KELLER</h2>")
    }

    @Test
    fun `Assert author does not exist with status code 404`() {
        logger.info(">> Assert author does not exists: status code 404")
        val entity = restTemplate.getForEntity<String>("/author/login-who-does-not-exist")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @AfterAll
    fun teardown() {
        logger.info(">> Tear down")
    }

}
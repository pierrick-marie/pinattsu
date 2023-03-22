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
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
class ArticlePageTests(@Autowired val restTemplate: TestRestTemplate) {

	private val logger: Logger = LogManager.getLogger(ArticlePageTests::class.java)

	@BeforeAll
	fun setup() {
		logger.info(">> Setup")
	}

	@Test
	fun `Assert articles page, content and status code`() {
		logger.info(">> Assert articles page, content and status code")
		val entity = restTemplate.getForEntity<String>("/articles")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains("<title>Articles</title>",
			"<h2>Annie Easley</h2>", "<h2>Mary Keller</h2>", "<h5>Biography of Annie</h5>",
			"<h5>Biography of Mary</h5>", "<h5>Lorem Ipsum</h5>")
	}

	@Test
	fun `Assert articles page by date, content and status code`() {
		logger.info(">> Assert articles page, content and status code")
		val entity = restTemplate.getForEntity<String>("/article/date/2022-12-01")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains("<title>Articles for 2022-12-01</title>")
	}

	@Test
	fun `Assert articles page by Annie, content and status code`() {
		logger.info(">> Assert articles page, content and status code")
		val entity = restTemplate.getForEntity<String>("/article/author/ae")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains("<title>Articles for Annie Easley</title>", "<h5>Biography of Annie</h5>", "<p>2023-03-21</p>")
	}

	@Test
	fun `Assert article of author who does not exist with status code 404`() {
		logger.info(">> Assert article does not exists: status code 404")
		val entity = restTemplate.getForEntity<String>("/article/author/login-who-does-not-exist")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
	}

	@AfterAll
	fun teardown() {
		logger.info(">> Tear down")
	}

}
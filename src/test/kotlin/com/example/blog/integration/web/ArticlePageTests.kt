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
class ArticlePageTests(@Autowired val restTemplate: TestRestTemplate) {

	private val logger: Logger = LogManager.getLogger(ArticlePageTests::class.java)

	@BeforeAll
	fun setup() {
		logger.info(">> Setup")
	}

	@Test
	fun `Assert articles page, content and status code`() {
		println(">> Assert articles page, content and status code")
		val entity = restTemplate.getForEntity<String>("/articles")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains("<h1>Articles</h1>", "My first article", "My second article", "The best article ever")
	}

	@Test
	fun `Assert articles page by date, content and status code`() {
		println(">> Assert articles page, content and status code")
		val entity = restTemplate.getForEntity<String>("/article/date/2022-12-01")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains("<h1>Articles</h1>", "My first article", "pierrick marie")
	}

	@Test
	fun `Assert articles page by author pm1, content and status code`() {
		println(">> Assert articles page, content and status code")
		val entity = restTemplate.getForEntity<String>("/article/author/pm1")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains("<h1>Articles</h1>", "My first article", "My second article", "pierrick marie")
	}

	@AfterAll
	fun teardown() {
		logger.info(">> Tear down")
	}

}
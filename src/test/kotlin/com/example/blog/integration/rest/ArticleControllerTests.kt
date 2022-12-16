package com.example.blog.`rest-api`

import com.example.blog.entity.Article
import com.example.blog.entity.Author
import com.example.blog.repository.ArticleRepository
import com.example.blog.repository.AuthorRepository
import com.example.blog.service.AuthorService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@WebMvcTest
class ArticleControllerTests(@Autowired val mockMvc: MockMvc) {

//	@MockkBean
//	private lateinit var authorRepository: AuthorRepository
//
//	@MockkBean
//	private lateinit var articleRepository: ArticleRepository

//	@MockkBean
//	private lateinit var authorService: AuthorService
//
//	@MockkBean
//	private lateinit var articleService: AuthorService

	private val logger: Logger = LogManager.getLogger(ArticleControllerTests::class.java)

	@Test
	fun `List article`() {
		val juergen = Author(
			login = "springjuergen",
			firstName = "Juergen",
			lastName = "Hoeller")
		val spring5Article = Article(
			title = "Spring Framework 5.0 goes GA",
			content = "Dear Spring community ...",
//			author = juergen
		)
		val spring43Article = Article(
			title = "Spring Framework 4.3 goes GA",
			content = "Dear Spring community ...",
//			author = juergen
		)

//		every { articleRepository.findAllByOrderByDateDesc() } returns listOf(spring5Article, spring43Article)

		mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].author.login").value(juergen.login))
			.andExpect(jsonPath("\$.[0].title").value(spring5Article.title))
			.andExpect(jsonPath("\$.[1].author.login").value(juergen.login))
			.andExpect(jsonPath("\$.[1].title").value(spring43Article.title))
	}
}
package com.example.blog

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@WebMvcTest
class RestControllersTests(@Autowired val mockMvc: MockMvc) {

	@MockkBean
	private lateinit var authorRepository: AuthorRepository

	@MockkBean
	private lateinit var articleRepository: ArticleRepository

	private val logger: Logger = LogManager.getLogger(RestControllersTests::class.java)

	@Test
	fun `List article`() {
		val juergen = Author("springjuergen", "Juergen", "Hoeller")
		val spring5Article = Article("Spring Framework 5.0 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen)
		val spring43Article = Article("Spring Framework 4.3 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen)

		every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(spring5Article, spring43Article)

		mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].author.login").value(juergen.login))
			.andExpect(jsonPath("\$.[0].slug").value(spring5Article.slug))
			.andExpect(jsonPath("\$.[1].author.login").value(juergen.login))
			.andExpect(jsonPath("\$.[1].slug").value(spring43Article.slug))
	}
}
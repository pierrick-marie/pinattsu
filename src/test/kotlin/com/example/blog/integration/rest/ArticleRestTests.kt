package com.example.blog.integration.rest

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ArticleRestTests @Autowired constructor(val mockMvc: MockMvc) {

	@Test
	fun `run ordered tests`() {
		`Get first article`()
		`Get all articles`()
		`Post new article`()
		`Post new article again`()
		`Get all first articles and lastly added`()
		`Delete first article of the list`()
		`Get new first article`()
		`Update last article of the list`()
	}

	fun `Get first article`() {
		mockMvc.perform(
			get("/api/article/1").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.title").value("Biography of Annie"))
			.andExpect(jsonPath("\$.author.login").value("ae"))

		mockMvc.perform(
			get("/api/article/XXX").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isBadRequest)
	}

	fun `Get all articles`() {
		mockMvc.perform(
			get("/api/article").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].title").value("Biography of Annie"))
			.andExpect(jsonPath("\$.[0]author.login").value("ae"))
			.andExpect(jsonPath("\$.[1].title").value("Lorem Ipsum"))
			.andExpect(jsonPath("\$.[1]author.login").value("ae"))
			.andExpect(jsonPath("\$.[2].title").value("Biography of Mary"))
			.andExpect(jsonPath("\$.[2]author.login").value("mk"))
	}

	fun `Post new article`() {
		this.mockMvc.perform(
			post("/api/article")
				.content("{\"title\": \"New Biography of Mary\",\"content\": \"Mary was a wonderful woman!\",\"author\": \"mk\"}")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title").value("New Biography of Mary"))
			.andExpect(jsonPath("$.content").value("Mary was a wonderful woman!"))
			.andExpect(jsonPath("$.author.login").value("mk"))

		mockMvc.perform(
			get("/api/article/4").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.title").value("New Biography of Mary"))
			.andExpect(jsonPath("\$.author.login").value("mk"))
	}

	fun `Post new article again`() {
		this.mockMvc.perform(
			post("/api/article")
				.content("{\"title\": \"Lorem Ipsum V2\",\"content\": \"Lorem Ipsum again\",\"author\": \"mk\"}")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title").value("Lorem Ipsum V2"))
			.andExpect(jsonPath("$.content").value("Lorem Ipsum again"))
			.andExpect(jsonPath("$.author.login").value("mk"))

		mockMvc.perform(
			get("/api/article/5").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.title").value("Lorem Ipsum V2"))
			.andExpect(jsonPath("\$.author.login").value("mk"))
	}

	fun `Get all first articles and lastly added`() {
		mockMvc.perform(
			get("/api/article").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].title").value("Biography of Annie"))
			.andExpect(jsonPath("\$.[1].title").value("Lorem Ipsum"))
			.andExpect(jsonPath("\$.[2].title").value("Biography of Mary"))
			.andExpect(jsonPath("\$.[3].title").value("New Biography of Mary"))
			.andExpect(jsonPath("\$.[4].title").value("Lorem Ipsum V2"))
	}

	fun `Delete first article of the list`() {
		mockMvc.perform(
			delete("/api/article/1").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
	}

	fun `Get new first article`() {
		mockMvc.perform(
			get("/api/article/1").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isNotFound)

		mockMvc.perform(
			get("/api/article/2").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.title").value("Lorem Ipsum"))
			.andExpect(jsonPath("\$.author.login").value("ae"))
	}

	fun `Update last article of the list`() {
		this.mockMvc.perform(
			put("/api/article/5")
				.content("{\"title\": \"Lorem Ipsum V3\",\"content\": \"Lorem Ipsum again and again\",\"author\": \"mk\"}")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title").value("Lorem Ipsum V3"))
			.andExpect(jsonPath("$.content").value("Lorem Ipsum again and again"))
			.andExpect(jsonPath("$.author.login").value("mk"))

		mockMvc.perform(
			get("/api/article/5").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.title").value("Lorem Ipsum V3"))
			.andExpect(jsonPath("\$.author.login").value("mk"))
	}
}
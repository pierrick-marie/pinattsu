package com.example.blog.integration.rest

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ArticleTests @Autowired constructor(val mockMvc: MockMvc) {

	@Test
	fun `run ordered tests`() {
		`Get first article`()
		`Get all articles`()
		`Post new article`()
		`Post new article again`()
		`Get all articles with new`()
		`Delete first article`()
		`Get new first article`()
		`Update last article`()
	}

	fun `Get first article`() {
		mockMvc.perform(
			get("/api/article/1").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.title").value("My first article"))
			.andExpect(jsonPath("\$.author.login").value("pm1"))

		mockMvc.perform(
			get("/api/article/4").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isNotFound)
	}

	fun `Get all articles`() {
		mockMvc.perform(
			get("/api/article").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].title").value("My first article"))
			.andExpect(jsonPath("\$.[0]author.login").value("pm1"))
			.andExpect(jsonPath("\$.[1].title").value("My second article"))
			.andExpect(jsonPath("\$.[1]author.login").value("pm1"))
			.andExpect(jsonPath("\$.[2].title").value("The best article ever"))
			.andExpect(jsonPath("\$.[2]author.login").value("pm2"))
	}

	fun `Post new article`() {
		this.mockMvc.perform(
			post("/api/article")
				.content("{\"title\": \"My SUPER article from PM2\",\"content\": \"Lorem Ipsum |o|\",\"author\": \"pm2\"}")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title").value("My SUPER article from PM2"))
			.andExpect(jsonPath("$.content").value("Lorem Ipsum |o|"))
			.andExpect(jsonPath("$.author.login").value("pm2"))

		mockMvc.perform(
			get("/api/article/4").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.title").value("My SUPER article from PM2"))
			.andExpect(jsonPath("\$.author.login").value("pm2"))
	}

	fun `Post new article again`() {
		this.mockMvc.perform(
			post("/api/article")
				.content("{\"title\": \"My SUPER article V2\",\"content\": \"Lorem Ipsum |o|\",\"author\": \"pm2\"}")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title").value("My SUPER article V2"))
			.andExpect(jsonPath("$.content").value("Lorem Ipsum |o|"))
			.andExpect(jsonPath("$.author.login").value("pm2"))

		mockMvc.perform(
			get("/api/article/5").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.title").value("My SUPER article V2"))
			.andExpect(jsonPath("\$.author.login").value("pm2"))
	}

	fun `Get all articles with new`() {
		mockMvc.perform(
			get("/api/article").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].title").value("My first article"))
			.andExpect(jsonPath("\$.[1].title").value("My second article"))
			.andExpect(jsonPath("\$.[2].title").value("The best article ever"))
			.andExpect(jsonPath("\$.[3].title").value("My SUPER article from PM2"))
			.andExpect(jsonPath("\$.[4].title").value("My SUPER article V2"))
	}

	fun `Delete first article`() {
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
			.andExpect(jsonPath("\$.title").value("My second article"))
			.andExpect(jsonPath("\$.author.login").value("pm1"))
	}

	fun `Update last article`() {
		this.mockMvc.perform(
			put("/api/article/5")
				.content("{\"title\": \"Updated article title\",\"content\": \"Updated article content\",\"author\": \"pm2\"}")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title").value("Updated article title"))
			.andExpect(jsonPath("$.content").value("Updated article content"))
			.andExpect(jsonPath("$.author.login").value("pm2"))

		mockMvc.perform(
			get("/api/article/5").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.title").value("Updated article title"))
			.andExpect(jsonPath("\$.author.login").value("pm2"))
	}
}
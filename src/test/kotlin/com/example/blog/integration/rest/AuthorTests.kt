package com.example.blog.integration.rest

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthorTests @Autowired constructor(val mockMvc: MockMvc) {

	@Test
	fun `run ordered tests`() {
		`Get first author by login`()
		`Get all authors`()
		`Post new author`()
		`Get all authors with new`()
		`Delete first author by login`()
		`Update last author`()
	}

	fun `Get first author by login`() {
		mockMvc.perform(
			get("/api/author/pm1").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.login").value("pm1"))
			.andExpect(jsonPath("\$.firstName").value("pierrick"))
			.andExpect(jsonPath("\$.lastName").value("marie"))

		mockMvc.perform(
			get("/api/author/pm3").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isNotFound)
	}

	fun `Get all authors`() {
		mockMvc.perform(
			get("/api/author").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].login").value("pm1"))
			.andExpect(jsonPath("\$.[1].login").value("pm2"))
	}

	fun `Post new author`() {
		this.mockMvc.perform(
			post("/api/author")
				.content("{\"login\": \"pm3\",\"firstName\": \"first3\",\"lastName\": \"last3\"}")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.login").value("pm3"))
			.andExpect(jsonPath("$.firstName").value("first3"))
			.andExpect(jsonPath("$.lastName").value("last3"))

		this.mockMvc.perform(
			post("/api/author")
				.content("{\"login\": \"pm3\",\"firstName\": \"first3\",\"lastName\": \"last3\"}")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isInternalServerError)
	}

	fun `Get all authors with new`() {
		mockMvc.perform(
			get("/api/author").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].login").value("pm1"))
			.andExpect(jsonPath("\$.[1].login").value("pm2"))
			.andExpect(jsonPath("\$.[2].login").value("pm3"))
	}

	fun `Delete first author by login`() {
		// There are three articles in database. The two first are written by pm1
		mockMvc.perform(
			get("/api/article").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0]author.login").value("pm1"))
			.andExpect(jsonPath("\$.[1]author.login").value("pm1"))
			.andExpect(jsonPath("\$.[2]author.login").value("pm2"))

		// Delete pm1
		mockMvc.perform(
			delete("/api/author/pm1").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)

		// There are pm2 and pm3 in database
		mockMvc.perform(
			get("/api/author").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].login").value("pm2"))
			.andExpect(jsonPath("\$.[1].login").value("pm3"))

		// The two first articles from pm1 have been deleted
		mockMvc.perform(
			get("/api/article").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0]author.login").value("pm2"))
	}

	fun `Update last author`() {
		this.mockMvc.perform(
			MockMvcRequestBuilders.put("/api/author/pm3")
				.content("{\"login\": \"pm4\",\"firstName\": \"F4\",\"lastName\": \"L4\"}")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.login").value("pm4"))
			.andExpect(jsonPath("$.firstName").value("F4"))
			.andExpect(jsonPath("$.lastName").value("L4"))

		mockMvc.perform(
			get("/api/author").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].login").value("pm2"))
			.andExpect(jsonPath("\$.[1].login").value("pm4"))
			.andExpect(jsonPath("\$.[1].firstName").value("F4"))
	}
}
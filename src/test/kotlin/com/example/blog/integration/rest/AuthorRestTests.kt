package com.example.blog.integration.rest

import io.mockk.InternalPlatformDsl.toArray
import org.hamcrest.collection.IsCollectionWithSize
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class AuthorRestTests @Autowired constructor(val mockMvc: MockMvc) {

	@Test
	fun `run ordered tests`() {
		`Get first author by login`()
		`Get all authors`()
		`Post new author Katherine Johnson`()
		`Get all authors included Katherine`()
		`Delete Annie by her login`()
		`Update Katherine`()
	}

	fun `Get first author by login`() {
		mockMvc.perform(
			get("/api/author/ae").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.login").value("ae"))
			.andExpect(jsonPath("\$.firstName").value("Annie"))
			.andExpect(jsonPath("\$.lastName").value("Easley"))

		mockMvc.perform(
			get("/api/author/login-does-not-exist").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isNotFound)
	}

	fun `Get all authors`() {
		mockMvc.perform(
			get("/api/author").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].login").value("ae"))
			.andExpect(jsonPath("\$.[1].login").value("mk"))
	}

	fun `Post new author Katherine Johnson`() {
		this.mockMvc.perform(
			post("/api/author")
				.content("{\"login\": \"kj\",\"firstName\": \"Katherine\",\"lastName\": \"Johnson\"}")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.login").value("kj"))
			.andExpect(jsonPath("$.firstName").value("Katherine"))
			.andExpect(jsonPath("$.lastName").value("Johnson"))

		this.mockMvc.perform(
			post("/api/author")
				.content("{\"login\": \"kj\",\"firstName\": \"Katherine\",\"lastName\": \"Johnson\"}")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isInternalServerError)
	}

	fun `Get all authors included Katherine`() {
		mockMvc.perform(
			get("/api/author").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].login").value("ae"))
			.andExpect(jsonPath("\$.[1].login").value("mk"))
			.andExpect(jsonPath("\$.[2].login").value("kj"))
	}

	fun `Delete Annie by her login`() {
		mockMvc.perform(
			get("/api/article").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0]author.login").exists())
			.andExpect(jsonPath("\$.[1]author.login").exists())
			.andExpect(jsonPath("\$.[2]author.login").exists())
			.andExpect(jsonPath("\$.[3]author.login").doesNotExist())

		// Delete Annie
		mockMvc.perform(
			delete("/api/author/ae").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)

		// There are still Mary and Katherine in database
		mockMvc.perform(
			get("/api/author").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].login").value("mk"))
			.andExpect(jsonPath("\$.[1].login").value("kj"))

		// The two articles of Annie have been removed
		mockMvc.perform(
			get("/api/article").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0]author.login").value("mk"))
	}

	fun `Update Katherine`() {
		this.mockMvc.perform(
			MockMvcRequestBuilders.put("/api/author/kj")
				.content("{\"login\": \"rp\",\"firstName\": \"Radia\",\"lastName\": \"Perlman\"}")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.login").value("rp"))
			.andExpect(jsonPath("$.firstName").value("Radia"))
			.andExpect(jsonPath("$.lastName").value("Perlman"))

		mockMvc.perform(
			get("/api/author").accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("\$.[0].login").value("mk"))
			.andExpect(jsonPath("\$.[1].login").value("rp"))
			.andExpect(jsonPath("\$.[1].firstName").value("Radia"))
	}
}
package com.example.blog.entity

import com.example.blog.entity.render.apiRender
import com.example.blog.entity.render.webRender
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class AuthorRenderTests {

	val annie = Author(
		login = "ae",
		firstName = "Annie",
		lastName = "Easley",
	)

	@Test
	fun `Test web render author`() {

		val webRenderedJuergen = annie.webRender()
		assertThat(webRenderedJuergen.login).isEqualTo(annie.login)
		assertThat(webRenderedJuergen.firstName).isEqualTo(annie.firstName)
		assertThat(webRenderedJuergen.lastName).isEqualTo(annie.lastName.uppercase())
		assertThat(webRenderedJuergen.description).isEqualTo("Default description")
	}

	@Test
	fun `Test REST API render author`() {

		val apiRenderedJuergen = annie.apiRender()
		assertThat(apiRenderedJuergen.login).isEqualTo(annie.login)
		assertThat(apiRenderedJuergen.firstName).isEqualTo(annie.firstName)
		assertThat(apiRenderedJuergen.lastName).isEqualTo(annie.lastName.uppercase())
		assertThat(apiRenderedJuergen.description).isEqualTo("Default description")
	}
}
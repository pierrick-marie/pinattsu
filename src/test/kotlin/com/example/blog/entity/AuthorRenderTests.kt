package com.example.blog.entity

import com.example.blog.entity.render.apiRender
import com.example.blog.entity.render.webRender
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Assertions.assertThat

@SpringBootTest
class AuthorRenderTests {

	val juergen = Author(
		login = "juergen",
		firstName = "Junior",
		lastName = "Da Silva",
	)

	@Test
	fun `Test web render author`() {

		val webRenderedJuergen = juergen.webRender()
		assertThat(webRenderedJuergen.login).isEqualTo(juergen.login)
		assertThat(webRenderedJuergen.firstName).isEqualTo(juergen.firstName)
		assertThat(webRenderedJuergen.lastName).isEqualTo(juergen.lastName.uppercase())
		assertThat(webRenderedJuergen.description).isEqualTo("Default description")
	}

	@Test
	fun `Test REST API render author`() {

		val apiRenderedJuergen = juergen.apiRender()
		assertThat(apiRenderedJuergen.login).isEqualTo(juergen.login)
		assertThat(apiRenderedJuergen.firstName).isEqualTo(juergen.firstName)
		assertThat(apiRenderedJuergen.lastName).isEqualTo(juergen.lastName.uppercase())
		assertThat(apiRenderedJuergen.description).isEqualTo("Default description")
	}
}
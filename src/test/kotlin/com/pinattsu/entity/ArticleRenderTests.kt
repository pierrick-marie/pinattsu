package com.pinattsu.entity

import com.pinattsu.entity.render.apiRender
import com.pinattsu.entity.render.webRender
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class ArticleRenderTests {

	val annie = Author(
		login = "ae",
		firstName = "Annie",
		lastName = "Easley",
	)

	val biography = Article(
		id = 1,
		title = "My biography",
		content = """
			Annie was born in 1933 in Birmingham Alabama, and died in 2011. She attended Xavier University where she majored in pharmacy for around 2 years. Shortly after finishing University, she met her husband and they moved to Cleveland. This is where Annie’s life changed for the better. As there was no pharmaceutical school nearby, she applied for a job at the National Advisory Committee for Aeronautics (NACA) and within 2 weeks had started working there. She was one of four African Americans who worked there and developed and implemented code which led to the development of the batteries used in hybrid cars. She is well known for being one of the famous women in technology for encouraging women and people of her colour to study and enter STEM fields.
		""".trimIndent(),
		author = annie
	)

	@Test
	fun `Test web render author`() {

		val webRenderedArticle = biography.webRender()
		assertThat(webRenderedArticle.title).isEqualTo(biography.title)
		assertThat(webRenderedArticle.content).isEqualTo(biography.content)
		assertThat(webRenderedArticle.date).isEqualTo(biography.date)
		assertThat(webRenderedArticle.author).isEqualTo(annie)
	}

	@Test
	fun `Test REST API render author`() {

		val apiRenderedArticle = biography.apiRender()
		assertThat(apiRenderedArticle.id).isEqualTo(biography.id)
		assertThat(apiRenderedArticle.title).isEqualTo(biography.title)
		assertThat(apiRenderedArticle.content).isEqualTo(biography.content)
		assertThat(apiRenderedArticle.date).isEqualTo(biography.date)
		assertThat(apiRenderedArticle.author).isEqualTo(annie.login)
	}
}
package com.example.blog.entity

import com.example.blog.entity.render.apiRender
import com.example.blog.entity.render.webRender
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Assertions.assertThat

@SpringBootTest
class ArticleRenderTests {

	val juergen = Author(
		login = "juergen",
		firstName = "Junior",
		lastName = "Da Silva",
	)

	val bestSeller = Article(
		id = 1,
		title = "My best seller",
		content = "Surprise!",
		author = juergen
	)

	@Test
	fun `Test web render author`() {

		val webRenderedArticle = bestSeller.webRender()
		assertThat(webRenderedArticle.title).isEqualTo(bestSeller.title)
		assertThat(webRenderedArticle.content).isEqualTo(bestSeller.content)
		assertThat(webRenderedArticle.date).isEqualTo(bestSeller.date)
		assertThat(webRenderedArticle.author).isEqualTo(juergen)
	}

	@Test
	fun `Test REST API render author`() {

		val apiRenderedArticle = bestSeller.apiRender()
		assertThat(apiRenderedArticle.id).isEqualTo(bestSeller.id)
		assertThat(apiRenderedArticle.title).isEqualTo(bestSeller.title)
		assertThat(apiRenderedArticle.content).isEqualTo(bestSeller.content)
		assertThat(apiRenderedArticle.date).isEqualTo(bestSeller.date)
		assertThat(apiRenderedArticle.author).isEqualTo(juergen.login)
	}
}
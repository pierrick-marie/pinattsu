package com.example.blog

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

@Controller
class BlogController(private val repository: ArticleRepository, private val properties: BlogProperties) {

	@GetMapping("/")
	fun blog(model: Model): String {
		model["title"] = "My first Blog"
		model["banner"] = properties.banner
		model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }

		return "blog"
	}

	@GetMapping("/article/{slug}")
	fun article(@PathVariable slug: String, model: Model): String {
		val article = repository.findBySlug(slug)
			?.render()
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")

		model["title"] = article.title
		model["article"] = article

		return "article"
	}

	fun Article.render() = RenderedArticle(
		title,
		headline,
		content,
		author,
		slug,
		addedAt.format()
	)

	data class RenderedArticle(
		val title: String,
		val headline: String,
		val content: String,
		val author: Author,
		val slug: String,
		val addedAt: String,
	)
}

package com.example.blog.controller.web

import com.example.blog.entity.render.webRender
import com.example.blog.property.DefaultProperties
import com.example.blog.service.ArticleService
import com.example.blog.service.AuthorService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class ArticleController(
	private val articleService: ArticleService,
	private val authorService: AuthorService,
	private val properties: DefaultProperties
) {

	private val logger: Logger = LogManager.getLogger(ArticleController::class.java)

	@GetMapping("/articles")
	fun articles(model: Model): String {
		val articles = articleService.getAll()

		model["title"] = "Articles"
		model["banner"] = properties.banner
		model["articles"] = articles

		return "entities/articles"
	}

	@GetMapping("/article/date/{date}")
	fun date(@PathVariable date: String, model: Model): String {
		val articles = articleService.getByDate(date).map { it.webRender() }

		model["title"] = "Articles for " + date
		model["banner"] = properties.banner
		model["articles"] = articles

		return "entities/articles"
	}

	@GetMapping("/article/author/{login}")
	fun author(@PathVariable login: String, model: Model): String {

		val author = authorService.getByLogin(login)

		val articles = articleService.getByAuthor(author)

		model["title"] = "Articles for ${author.firstName} ${author.lastName}"
		model["banner"] = properties.banner
		model["articles"] = articles

		return "entities/articles"
	}

}
package com.example.blog.controller

import com.example.blog.property.DefaultProperties
import com.example.blog.entity.ArticleRepository
import com.example.blog.entity.AuthorRepository
import com.example.blog.entity.render
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

@Controller
class ArticleController(
	private val articleRepository: ArticleRepository,
	private val authorRepository: AuthorRepository,
	private val properties: DefaultProperties
) {

	private val logger: Logger = LogManager.getLogger(ArticleController::class.java)

	@GetMapping("/articles")
	fun articles(model: Model): String {
		val articles = articleRepository.findAll().map { it.render() }

		model["title"] = "Articles"
		model["banner"] = properties.banner
		model["articles"] = articles

		return "entities/articles"
	}

	@GetMapping("/article/date/{formatedDate}")
	fun date(@PathVariable formatedDate: String, model: Model): String {
		val articles = articleRepository.findByFormatedDate(formatedDate).map { it.render() }

		model["title"] = "Articles for " + formatedDate
		model["banner"] = properties.banner
		model["articles"] = articles

		return "entities/articles"
	}

	@GetMapping("/article/author/{login}")
	fun author(@PathVariable login: String, model: Model): String {

		val author = authorRepository.findByLogin(login)
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This author does not exist")

		val articles = articleRepository.findByAuthor(author).map { it.render() }

		model["title"] = "Articles for ${author.firstName} ${author.lastName}"
		model["banner"] = properties.banner
		model["articles"] = articles

		return "entities/articles"
	}

}
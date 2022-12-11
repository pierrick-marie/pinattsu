package com.example.blog.controller

import com.example.blog.BlogProperties
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
class AuthorController(
	private val authorRepository: AuthorRepository,
	private val properties: BlogProperties) {

	private val logger: Logger = LogManager.getLogger(AuthorController::class.java)

	@GetMapping("/authors")
	fun blog(model: Model): String {

		val authors = authorRepository.findAll()
		// logger.info("Get authors: " + authors)

		model["title"] = "Authors"
		model["banner"] = properties.banner
		model["authors"] = authors.map { it.render() }

		return "authors"
	}

	@GetMapping("/author/{login}")
	fun author(@PathVariable login: String, model: Model): String {
		val author = authorRepository.findByLogin(login)
			?.render()
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This author does not exist")

		model["title"] = author.firstName
		model["banner"] = properties.banner
		model["author"] = author

		return "author-profile"
	}
}
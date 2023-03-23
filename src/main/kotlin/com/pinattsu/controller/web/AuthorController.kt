package com.pinattsu.controller.web

import com.pinattsu.entity.render.webRender
import com.pinattsu.property.BlogProperties
import com.pinattsu.service.AuthorService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class AuthorController(
	private val authorService: AuthorService,
	private val properties: BlogProperties
) {

	private val logger: Logger = LogManager.getLogger(AuthorController::class.java)

	@GetMapping("/authors")
	fun blog(model: Model): String {

		val authors = authorService.getAll()

		model["title"] = "Authors"
		model["banner"] = properties.banner
		model["authors"] = authors.map { it.webRender() }

		return "entities/authors"
	}

	@GetMapping("/author/{login}")
	fun author(@PathVariable login: String, model: Model): String {
		val author = authorService.getByLogin(login).webRender()

		model["title"] = author.firstName
		model["banner"] = properties.banner
		model["author"] = author

		return "entities/author-profile"
	}
}
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
class MainController(
	private val authorRepository: AuthorRepository,
	private val properties: BlogProperties) {

	private val logger: Logger = LogManager.getLogger(MainController::class.java)

	@GetMapping("/")
	fun blog(model: Model): String {

		model["title"] = properties.title
		model["banner"] = properties.banner

		return "blog"
	}
}
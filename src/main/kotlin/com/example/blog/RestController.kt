package com.example.blog

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/article")
class ArticleController(private val repository: ArticleRepository) {

	@GetMapping("/")
	fun findAll() = repository.findAllByOrderByAddedAtDesc().map { it.render() }

	fun Article.render() = BlogController.RenderedArticle(
		title,
		headline,
		content,
		author,
		slug,
		addedAt.format()
	)
}

@RestController
@RequestMapping("api/author")
class AuthorController(private val repository: AuthorRepository) {

	@GetMapping("/")
	fun findAll() = repository.findAll().map { it.render() }

	fun Author.render() = RenderedAuthor (
		login,
		firstname,
		lastname,
	)

	data class RenderedAuthor (
		val login: String,
		val firstName: String,
		val lastName: String,
	)
}
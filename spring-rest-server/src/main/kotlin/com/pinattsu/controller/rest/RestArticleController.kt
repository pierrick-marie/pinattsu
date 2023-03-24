package com.pinattsu.controller.rest

import com.pinattsu.entity.Article
import com.pinattsu.entity.render.ApiRenderedArticle
import com.pinattsu.service.ArticleService
import com.pinattsu.service.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/article")
class RestArticleController(
	private val articleService: ArticleService,
	private val authorService: AuthorService
) {

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	fun findAll() = articleService.getAll()

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun createArticle(@RequestBody article: ApiRenderedArticle) = articleService.create(Article(
		title = article.title,
		content = article.content,
		date = article.date?:"",
		author = article.author?.let { authorService.getByLogin(it) }
	))

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	fun updateArticle(@PathVariable("id") id: Long, @RequestBody article: ApiRenderedArticle) = articleService.updateById(id, Article(
		title = article.title,
		content = article.content,
		date = article.date?:"",
		author = article.author?.let { authorService.getByLogin(it) }
	))

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	fun get(@PathVariable("id") id: Long) = articleService.getById(id)

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	fun delete(@PathVariable("id") id: Long) = articleService.removeById(id)
}
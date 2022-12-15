package com.example.blog.controller

import com.example.blog.entity.ApiRenderedArticle
import com.example.blog.service.ArticleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/article")
class RestArticleController(private val articleService: ArticleService) {

	@GetMapping
	fun findAll() = articleService.getAll()

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun createArticle(@RequestBody article: ApiRenderedArticle) = articleService.create(article)

	@PutMapping("/{id}")
	fun updateArticle(@PathVariable("id") id: Long, @RequestBody article: ApiRenderedArticle) = articleService.updateById(id, article)

	@GetMapping("/{id}")
	fun get(@PathVariable("id") id: Long) = articleService.getById(id)

	@DeleteMapping("/{id}")
	fun delete(@PathVariable("id") id: Long) = articleService.removeById(id)
}
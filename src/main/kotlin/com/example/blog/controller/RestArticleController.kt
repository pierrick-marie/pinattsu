package com.example.blog.controller

import com.example.blog.entity.Article
import com.example.blog.entity.Author
import com.example.blog.service.ArticleService
import com.example.blog.service.AuthorService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("api/article")
class RestArticleController(private val articleService: ArticleService) {

	@GetMapping
	fun findAll() = articleService.getAll()

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun createArticle(@RequestBody article: Article) = articleService.create(article)

	@PutMapping("/{id}")
	fun updateArticle(@PathVariable("id") id: Long, @RequestBody article: Article) = articleService.update(id, article)

	@GetMapping("/{id}")
	fun get(@PathVariable("id") id: Long) = articleService.getById(id)

	@DeleteMapping("/{id}")
	fun delete(@PathVariable("id") id: Long) = articleService.remove(id)
}
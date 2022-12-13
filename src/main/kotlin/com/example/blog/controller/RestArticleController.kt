package com.example.blog.controller

import com.example.blog.repository.ArticleRepository
import com.example.blog.entity.render
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/article")
class RestArticleController(private val articleRepository: ArticleRepository) {

	@GetMapping("/")
	fun findAll() = articleRepository.findAllByOrderByDateDesc().map { it.render() }
}
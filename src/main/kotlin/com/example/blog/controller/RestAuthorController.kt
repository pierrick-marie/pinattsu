package com.example.blog.controller

import com.example.blog.repository.ArticleRepository
import com.example.blog.repository.AuthorRepository
import com.example.blog.entity.render
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/author")
class RestAuthorController(private val authorRepository: AuthorRepository) {

	@GetMapping("/")
	fun findAll() = authorRepository.findAll().map { it.render() }
}
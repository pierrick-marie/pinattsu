package com.example.blog.controller

import com.example.blog.entity.Author
import com.example.blog.service.AuthorService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("api/author")
class RestAuthorController(private val authorService: AuthorService) {

	@GetMapping
	fun findAll() = authorService.getAll()

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun createAuthor(@RequestBody author: Author) = authorService.create(author)

	@PutMapping("/{login}")
	fun updateAuthorByLogin(@PathVariable("login") login: String, @RequestBody author: Author) = authorService.updateByLogin(login, author)

	@GetMapping("/{id}")
	fun get(@PathVariable("id") id: Long) = authorService.getById(id)

	@DeleteMapping("/{id}")
	fun delete(@PathVariable("id") id: Long) = authorService.remove(id)
}
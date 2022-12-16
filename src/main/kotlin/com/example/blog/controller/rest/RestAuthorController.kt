package com.example.blog.controller.rest

import com.example.blog.entity.Author
import com.example.blog.service.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("api/author")
class RestAuthorController(
	private val authorService: AuthorService
) {

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	fun findAll() = authorService.getAll()

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun createAuthor(@RequestBody author: Author) = authorService.create(author)

	@PutMapping("/{login}")
	@ResponseStatus(HttpStatus.OK)
	fun updateAuthorByLogin(@PathVariable("login") login: String, @RequestBody author: Author) = authorService.updateByLogin(login, author)

	@GetMapping("/{login}")
	@ResponseStatus(HttpStatus.OK)
	fun get(@PathVariable("login") login: String) = authorService.getByLogin(login)

	@DeleteMapping("/{login}")
	@ResponseStatus(HttpStatus.OK)
	fun delete(@PathVariable("login") login: String) = authorService.removeByLogin(login)
}
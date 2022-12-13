package com.example.blog.controller

import com.example.blog.entity.Author
import com.example.blog.entity.render
import com.example.blog.repository.AuthorRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/author")
class RestAuthorController(private val authorRepository: AuthorRepository) {

	@GetMapping("/all")
	fun findAll() = authorRepository.findAll().map { it.render() }

	@PostMapping("/new")
	fun createAuthor(@RequestBody author: Author): ResponseEntity<Author?>? {
		return try {
			val _author: Author = authorRepository
				.save(Author(
					login = author.login,
					firstName = author.firstName,
					lastName = author.lastName,
					description = author.description ?: ""
				))
			ResponseEntity<Author?>(_author, HttpStatus.CREATED)
		} catch (e: Exception) {
			ResponseEntity<Author?>(null, HttpStatus.INTERNAL_SERVER_ERROR)
		}
	}

//	@PutMapping("/tutorials/{id}")
//	fun updateTutorial(@PathVariable("id") id: Long, @RequestBody tutorial: Tutorial): ResponseEntity<Tutorial?>? {
//		val tutorialData: Optional<Tutorial> = tutorialRepository.findById(id)
//		return if (tutorialData.isPresent()) {
//			val _tutorial: Tutorial = tutorialData.get()
//			_tutorial.setTitle(tutorial.getTitle())
//			_tutorial.setDescription(tutorial.getDescription())
//			_tutorial.setPublished(tutorial.isPublished())
//			ResponseEntity<Any?>(tutorialRepository.save(_tutorial), HttpStatus.OK)
//		} else {
//			ResponseEntity<Tutorial?>(HttpStatus.NOT_FOUND)
//		}
//	}
//
//	@DeleteMapping("/tutorials/{id}")
//	fun deleteTutorial(@PathVariable("id") id: Long): ResponseEntity<HttpStatus?>? {
//		return try {
//			tutorialRepository.deleteById(id)
//			ResponseEntity<HttpStatus?>(HttpStatus.NO_CONTENT)
//		} catch (e: Exception) {
//			ResponseEntity<HttpStatus?>(HttpStatus.INTERNAL_SERVER_ERROR)
//		}
//	}
}
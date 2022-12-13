package com.example.blog.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.springframework.data.repository.CrudRepository

@Entity
class Author (
	val login: String,
	var firstName: String,
	var lastName: String,
	var description: String? = null,
	@Id @GeneratedValue var id: Long? = null,
)

fun Author.render() = RenderedAuthor (
	login, firstName, lastName.uppercase(), description ?: "Default description"
)

data class RenderedAuthor (
	val login: String,
	val firstName: String,
	val lastName: String,
	val description: String
)

interface AuthorRepository : CrudRepository<Author, Long> {
	fun findByLogin(login: String): Author?
}
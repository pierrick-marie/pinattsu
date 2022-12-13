package com.example.blog.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.UniqueConstraint
import org.springframework.data.repository.CrudRepository

@Entity
class Author (
	@Column(unique = true)
	var login: String,
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


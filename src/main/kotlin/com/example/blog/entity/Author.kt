package com.example.blog.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.repository.CrudRepository

@Entity
class Author (
	@Column(unique = true, nullable = false)
	var login: String,
	var firstName: String,
	var lastName: String,
	var description: String? = null,
	@Id @GeneratedValue var id: Long? = null,
)

fun Author.apiRender() = RenderedAuthor (
	login, firstName, lastName.uppercase(), description ?: "Default description"
)

data class RenderedAuthor (
	val login: String,
	val firstName: String,
	val lastName: String,
	val description: String
)


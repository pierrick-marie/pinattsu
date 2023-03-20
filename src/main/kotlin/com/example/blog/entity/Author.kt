package com.example.blog.entity

import jakarta.persistence.*
import org.springframework.boot.autoconfigure.EnableAutoConfiguration

@Entity
class Author (
	@Column(unique = true, nullable = false)
	var login: String,
	var firstName: String,
	var lastName: String,
	var description: String? = null,
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
) {

	override fun equals(other: Any?): Boolean =
		(other is Author)
			  && id == other.id
			  && login == other.login
			  && firstName == other.firstName
			  && lastName == other.lastName

}
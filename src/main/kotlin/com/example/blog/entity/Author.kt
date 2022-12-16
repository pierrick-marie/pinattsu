package com.example.blog.entity

import jakarta.persistence.*

@Entity
class Author (
	@Column(unique = true, nullable = false)
	var login: String,
	var firstName: String,
	var lastName: String,
	var description: String? = null,
	@Id @GeneratedValue var id: Long? = null,
)




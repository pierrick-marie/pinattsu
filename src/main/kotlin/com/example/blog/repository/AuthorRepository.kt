package com.example.blog.repository

import com.example.blog.entity.Author
import org.springframework.data.repository.CrudRepository

interface AuthorRepository : CrudRepository<Author, Long> {
	fun findByLogin(login: String): Author?

	fun removeByLogin(login: String)
}
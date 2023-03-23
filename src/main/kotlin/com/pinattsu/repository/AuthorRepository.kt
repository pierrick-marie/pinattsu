package com.pinattsu.repository

import com.pinattsu.entity.Author
import org.springframework.data.repository.CrudRepository

interface AuthorRepository : CrudRepository<Author, Long> {
	fun findByLogin(login: String): Author?
}
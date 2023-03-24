package com.pinattsu.repository

import com.pinattsu.entity.Article
import com.pinattsu.entity.Author
import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long> {
	fun findByAuthor(author: Author): List<Article>
	fun findByDate(date: String): List<Article>
	fun findAllByOrderByDateDesc(): List<Article>
}
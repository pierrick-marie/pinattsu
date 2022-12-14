package com.example.blog.repository

import com.example.blog.entity.Article
import com.example.blog.entity.Author
import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long> {
	fun findByAuthor(author: Author): Iterable<Article>
	fun findByDate(date: String): Iterable<Article>

	fun findAllByOrderByDateDesc(): Iterable<Article>
}
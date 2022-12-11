package com.example.blog.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

@Entity
class Article (
	var title: String,
	var content: String,
	val date: LocalDateTime = LocalDateTime.now(),
	@ManyToOne
	val author: Author,
	@Id @GeneratedValue
	var id: Long? = null,
)

fun Article.render() = RenderedArticle (
	  title, content, date.toString(), author.render()
)

data class RenderedArticle (
	val title: String,
	val content: String,
	val date: String,
	val author: RenderedAuthor,
)

interface ArticleRepository : CrudRepository<Article, Long> {
	fun findByAuthor(author: Author): Iterable<Article>
	fun findByDate(date: LocalDateTime): Iterable<Article>
}
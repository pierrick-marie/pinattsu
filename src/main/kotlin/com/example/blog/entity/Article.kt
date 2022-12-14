package com.example.blog.entity

import com.example.blog.extension.format
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Article(
	var title: String,
	var content: String,
	var date: String = LocalDateTime.now().format(),
	@ManyToOne(fetch = FetchType.LAZY)
	var author: Author? = null,
	@Id @GeneratedValue
	var id: Long? = null,
)

fun Article.apiRender() = ApiRenderedArticle(
	id, title, content, date, author?.login
)

fun Article.webRender() = WebRenderedArticle(
	title, content, date, author
)

data class ApiRenderedArticle(
	val id: Long?,
	val title: String,
	val content: String,
	val date: String?,
	val author: String?,
)

data class WebRenderedArticle(
	val title: String,
	val content: String,
	val date: String?,
	val author: Author?,
)

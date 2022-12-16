package com.example.blog.entity.render

import com.example.blog.entity.Article
import com.example.blog.entity.Author

fun Article.webRender() = WebRenderedArticle(
	title, content, date, author
)

data class WebRenderedArticle(
	val title: String,
	val content: String,
	val date: String?,
	val author: Author?,
)

data class ApiRenderedArticle(
	val id: Long?,
	val title: String,
	val content: String,
	val date: String?,
	val author: String?,
)

fun Article.apiRender() = ApiRenderedArticle(
	id, title, content, date, author?.login
)
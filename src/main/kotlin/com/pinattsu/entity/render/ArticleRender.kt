package com.pinattsu.entity.render

import com.pinattsu.entity.Article
import com.pinattsu.entity.Author

fun Article.webRender() = WebRenderedArticle(
	title, content, date, author
)

data class WebRenderedArticle(
	val title: String,
	val content: String,
	val date: String?,
	val author: Author?,
)

fun Article.apiRender() = ApiRenderedArticle(
	id, title, content, date, author?.login
)

data class ApiRenderedArticle(
	val id: Long?,
	val title: String,
	val content: String,
	val date: String?,
	val author: String?,
)

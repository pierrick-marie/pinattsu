package com.example.blog.entity

import com.example.blog.extension.format
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

@Entity
class Article(
	var title: String,
	var content: String,
	var date: LocalDateTime = LocalDateTime.now(),
	var formatedDate: String = date.format(),
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	val author: Author,
	@Id @GeneratedValue
	var id: Long? = null,
)

fun Article.render() = RenderedArticle(
	title, content, formatedDate, author.render()
)

data class RenderedArticle(
	val title: String,
	val content: String,
	val date: String,
	val author: RenderedAuthor,
)

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


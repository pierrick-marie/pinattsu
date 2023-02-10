package com.example.blog.entity

import com.example.blog.extension.format
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

@Entity
class Article(
	var title: String,
	@Lob
	var content: String,
	var date: String = LocalDateTime.now().format(),
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	var author: Author? = null,
	@Id @GeneratedValue
	var id: Long? = null,
) {

	override fun equals(other: Any?): Boolean =
		(other is Article)
			  && id == other.id
			  && title == other.title
			  && content == other.content

}
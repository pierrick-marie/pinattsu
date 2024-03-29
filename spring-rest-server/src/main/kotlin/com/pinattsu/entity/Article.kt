package com.pinattsu.entity

import com.pinattsu.extension.format
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

@Entity
class Article(
	var title: String,
	@Lob
	@Column(columnDefinition="LONGTEXT")
	var content: String,
	var date: String = LocalDateTime.now().format(),
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	var author: Author? = null,
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
) {

	override fun equals(other: Any?): Boolean =
		(other is Article)
			  && id == other.id
			  && title == other.title
			  && content == other.content

}
package com.pinattsu.entity.render

import com.pinattsu.entity.Author

fun Author.webRender() = WebRenderedAuthor (
	login, firstName, lastName.uppercase(), description ?: "Default description"
)

data class WebRenderedAuthor (
	val login: String,
	val firstName: String,
	val lastName: String,
	val description: String
)

fun Author.apiRender() = ApiRenderedAuthor (
	login, firstName, lastName.uppercase(), description ?: "Default description"
)

data class ApiRenderedAuthor (
	val login: String,
	val firstName: String,
	val lastName: String,
	val description: String
)
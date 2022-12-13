package com.example.blog.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("blog")
data class DefaultProperties(var title: String, val banner: Banner) {
	 data class Banner(val title: String? = null, val content: String)
}

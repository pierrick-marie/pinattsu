package com.pinattsu.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "blog")
data class BlogProperties(var title: String, val banner: Banner) {
	 data class Banner(val title: String? = null, val content: String)
}

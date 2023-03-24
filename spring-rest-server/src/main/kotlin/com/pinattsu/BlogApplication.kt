package com.pinattsu

import com.pinattsu.property.BlogProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(BlogProperties::class)
class BlogApplication



fun main(args: Array<String>) {
	runApplication<com.pinattsu.BlogApplication>(*args)
}

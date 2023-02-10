package com.example.blog

import com.example.blog.property.DefaultProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@SpringBootApplication
@EnableConfigurationProperties(DefaultProperties::class)
class BlogApplication

@Bean
fun simpleCorsFilter(): FilterRegistrationBean<*> {
	val source = UrlBasedCorsConfigurationSource()
	val config = CorsConfiguration()
	config.allowCredentials = true
	// *** URL below needs to match the Vue client URL and port ***
	config.allowedOrigins = listOf("http://localhost:8443")
	config.allowedMethods = listOf("*")
	config.allowedHeaders = listOf("*")
	source.registerCorsConfiguration("/**", config)
	val bean: FilterRegistrationBean<*> = FilterRegistrationBean(CorsFilter(source))
	bean.order = Ordered.HIGHEST_PRECEDENCE
	return bean
}

fun main(args: Array<String>) {
	runApplication<BlogApplication>(*args)
}

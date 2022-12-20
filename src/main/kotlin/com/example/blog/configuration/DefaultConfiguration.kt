package com.example.blog.configuration

import com.example.blog.entity.Article
import com.example.blog.entity.Author
import com.example.blog.repository.ArticleRepository
import com.example.blog.repository.AuthorRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class DefaultConfiguration {

	@Bean
	fun init(authorRepository: AuthorRepository, articleRepository: ArticleRepository) = ApplicationRunner {

		val pm1 =
			Author(
				login = "pm1",
				firstName = "pierrick",
				lastName = "marie",
				description = "A jedi!"
			)

		val pm2 =
			Author(
				login = "pm2",
				firstName = "peter",
				lastName = "m."
			)


		val art1 =
			Article(
				title = "My first article",
				content = "Lorem Ipsum",
				date = "2022-12-01",
				author = pm1,
			)

		val art2 =
			Article(
				title = "My second article",
				content = "Lorem Ipsum v2",
				author = pm1,
			)

		val art3 =
			Article(
				title = "The best article ever",
				content = "Lorem Ipsum POWA",
				author = pm2,
			)

		authorRepository.save(pm1)
		authorRepository.save(pm2)

		articleRepository.save(art1)
		articleRepository.save(art2)
		articleRepository.save(art3)
	}

//	@Order(1)
//	@Bean
//	open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//
//		return http.requiresChannel().anyRequest().requiresSecure()
//			.and().authorizeHttpRequests()
//			.requestMatchers(HttpMethod.GET, "/").permitAll()
//			.anyRequest().authenticated()
//			.and()
//			.formLogin().loginPage("/login")
//			.permitAll()
//			.and()
//			.logout().permitAll()
//			.and().build()
//	}

//	@Bean
//	fun servletContainer(): ServletWebServerFactory? {
//		val connector = Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL)
//		connector.setPort(8080)
//		val tomcat = TomcatServletWebServerFactory()
//		tomcat.addAdditionalTomcatConnectors(connector)
//		return tomcat
//	}
}
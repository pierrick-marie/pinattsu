package com.example.blog.configuration

import com.example.blog.entity.Article
import com.example.blog.entity.Author
import com.example.blog.repository.ArticleRepository
import com.example.blog.repository.AuthorRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
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
}
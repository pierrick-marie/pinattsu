package com.example.blog.configuration

import com.example.blog.entity.Article
import com.example.blog.repository.ArticleRepository
import com.example.blog.entity.Author
import com.example.blog.repository.AuthorRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DefaultConfiguration {

	private lateinit var pm1: Author;
	private lateinit var pm2: Author;

	@Bean
	fun defaultAuthors(authorRepository: AuthorRepository) = ApplicationRunner {

		pm1 = authorRepository.save(Author(
			login = "pm1",
			firstName = "pierrick",
			lastName = "marie",
			description = "A jedi!")
		)

		pm2 = authorRepository.save(Author(
			login = "pm2",
			firstName = "peter",
			lastName = "m.")
		)
	}

	@Bean
	fun defaultArticles(articleRepository: ArticleRepository) = ApplicationRunner {

		articleRepository.save(Article(
			title = "My first article",
			content = "Lorem Ipsum",
			author = pm1,
		))

		articleRepository.save(Article(
			title = "My second article",
			content = "Lorem Ipsum v2",
			author = pm1,
		))

		articleRepository.save(Article(
			title = "The best article ever",
			content = "Lorem Ipsum POWA",
			author = pm2,
		))
	}
}
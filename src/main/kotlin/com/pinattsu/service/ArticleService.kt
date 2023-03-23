package com.pinattsu.service

import com.pinattsu.entity.Article
import com.pinattsu.entity.Author
import com.pinattsu.repository.ArticleRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ArticleService(val articleRepository: ArticleRepository, val authorService: AuthorService) {

	private val logger: Logger = LogManager.getLogger(ArticleService::class.java)

	fun getAll(): Iterable<Article> {
		return articleRepository.findAll()
	}

	fun getById(id: Long): Article {
		return articleRepository.findByIdOrNull(id)
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}

	fun getByDate(date: String): List<Article> {
		return articleRepository.findByDate(date)
	}

	fun getByAuthor(author: Author): List<Article> {
		return articleRepository.findByAuthor(author)
	}

	fun create(articleData: Article): Article {
		return try {
			val article = Article(
				title = articleData.title,
				content = articleData.content,
			)

			articleData.author?.let { authorService.getByLogin(it.login).also { article.author = it } }

			articleRepository.save(article)
		} catch (e: Exception) {
			logger.error(e.message)
			throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
		}
	}

	fun removeById(id: Long) {
		if (articleRepository.existsById(id)) articleRepository.deleteById(id)
		else throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}

	fun updateById(id: Long, articleData: Article): Article {

		var article = articleRepository.findByIdOrNull(id)
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

		article.title = articleData.title
		article.content = articleData.content
		articleData.date.let { article.date = it }

		articleData.author?.let {
			authorService.getByLogin(it.login).let {
				article.author = it
			}
		}

		return articleRepository.save(article)
	}
}
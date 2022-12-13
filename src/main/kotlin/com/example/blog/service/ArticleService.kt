package com.example.blog.service

import com.example.blog.entity.Article
import com.example.blog.entity.RenderedArticle
import com.example.blog.entity.render
import com.example.blog.repository.ArticleRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ArticleService(val articleRepository: ArticleRepository) {

	private val logger: Logger = LogManager.getLogger(ArticleService::class.java)

	fun getAll(): List<RenderedArticle> {
		return articleRepository.findAll().map { it.render() }
	}

	fun getById(id: Long): RenderedArticle {
		return articleRepository.findByIdOrNull(id)
			?.render()
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}

	fun create(article: Article): RenderedArticle {
		return try {
			articleRepository.save(article).render()
		} catch (e: Exception) {
			logger.error(e.message)
			throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
		}
	}

	fun remove(id: Long) {
		if (articleRepository.existsById(id)) articleRepository.deleteById(id)
		else throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}

	fun update(id: Long, article: Article): RenderedArticle {

		val _article = articleRepository.findByIdOrNull(id)
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

		if (article.author == null) article.author = _article.author
		article.id = id

		return articleRepository.save(article).render()
	}
}
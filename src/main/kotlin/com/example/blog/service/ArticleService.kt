package com.example.blog.service

import com.example.blog.entity.Article
import com.example.blog.entity.ApiRenderedArticle
import com.example.blog.entity.apiRender
import com.example.blog.repository.ArticleRepository
import com.example.blog.repository.AuthorRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ArticleService(val articleRepository: ArticleRepository, val authorRepository: AuthorRepository) {

	private val logger: Logger = LogManager.getLogger(ArticleService::class.java)

	fun getAll(): List<ApiRenderedArticle> {
		return articleRepository.findAll().map { it.apiRender() }
	}

	fun getById(id: Long): ApiRenderedArticle {
		return articleRepository.findByIdOrNull(id)
			?.apiRender()
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}

	fun create(articleData: ApiRenderedArticle): ApiRenderedArticle {
		return try {
			val article = Article(
				title = articleData.title,
				content = articleData.content,
			)

			articleData.author?.let { authorRepository.findByLogin(it).also { article.author = it } }

			articleRepository.save(article).apiRender()
		} catch (e: Exception) {
			logger.error(e.message)
			throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
		}
	}

	fun removeById(id: Long) {
		if (articleRepository.existsById(id)) articleRepository.deleteById(id)
		else throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}

	fun updateById(id: Long, articleData: ApiRenderedArticle): ApiRenderedArticle {

		var article = articleRepository.findByIdOrNull(id)
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

		article.title = articleData.title
		article.content = articleData.content
		articleData.date?.let { article.date = it }

		articleData.author?.let {
			authorRepository.findByLogin(it)?.let {
				article.author = it
			}
		}

		return articleRepository.save(article).apiRender()
	}
}
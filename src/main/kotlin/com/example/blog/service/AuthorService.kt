package com.example.blog.service

import com.example.blog.entity.Author
import com.example.blog.entity.RenderedAuthor
import com.example.blog.entity.apiRender
import com.example.blog.repository.AuthorRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthorService (val authorRepository: AuthorRepository) {

	private val logger: Logger = LogManager.getLogger(AuthorService::class.java)

	fun getAll(): List<RenderedAuthor> {
		return authorRepository.findAll().map{ it.apiRender() }
	}

	fun getById(id: Long): RenderedAuthor {
		return authorRepository.findByIdOrNull(id)
			?.apiRender()
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}

	fun getByLogin(login: String): RenderedAuthor {
		return authorRepository.findByLogin(login)
			?.apiRender()
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}

	fun create(author: Author): RenderedAuthor {
		return try {
			authorRepository.save(author).apiRender()
		} catch (e: Exception) {
			logger.error(e.message)
			throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
		}
	}

	fun remove(id: Long) {
		if (authorRepository.existsById(id)) authorRepository.deleteById(id)
		else throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}

	fun removeByLogin(login: String) {
		val author = authorRepository.findByLogin(login)
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
		authorRepository.delete(author)
	}

	fun update(id: Long, author: Author): RenderedAuthor {
		return if (authorRepository.existsById(id)) {
			author.id = id
			authorRepository.save(author).apiRender()
		} else throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}

	fun updateByLogin(login: String, author: Author): RenderedAuthor {
		val _author = authorRepository.findByLogin(login)
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

		author.id = _author.id

		return authorRepository.save(author).apiRender()
	}
}
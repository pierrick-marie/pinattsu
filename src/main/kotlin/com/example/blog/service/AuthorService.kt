package com.example.blog.service

import com.example.blog.entity.Author
import com.example.blog.entity.RenderedAuthor
import com.example.blog.entity.render
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
		return authorRepository.findAll().map{ it.render() }
	}

	fun getById(id: Long): RenderedAuthor {
		return authorRepository.findByIdOrNull(id)
			?.render()
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}

	fun create(author: Author): RenderedAuthor {
		return try {
			authorRepository.save(author).render()
		} catch (e: Exception) {
			logger.error(e.message)
			throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
		}
	}

	fun remove(id: Long) {
		if (authorRepository.existsById(id)) authorRepository.deleteById(id)
		else throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}

	fun update(id: Long, author: Author): RenderedAuthor {
		return if (authorRepository.existsById(id)) {
			author.id = id
			authorRepository.save(author).render()
		} else throw ResponseStatusException(HttpStatus.NOT_FOUND)
	}
}
package com.example.blog.service

import com.example.blog.entity.Author
import com.example.blog.repository.AuthorRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.server.ResponseStatusException
@ExtendWith(SpringExtension::class)
@DataJpaTest
class AuthorServiceTests @Autowired constructor(
	val entityManager: TestEntityManager,
	val authorRepository: AuthorRepository,
) {

	private val logger: Logger = LogManager.getLogger(AuthorServiceTests::class.java)

	private val authorService = AuthorService(authorRepository)
	private val juergen = Author("springjuergen", "Juergen", "Hoeller")
	private val peter = Author("peter", "Peter", "M.")

	@BeforeEach
	fun init() {

		authorRepository.deleteAll()

		authorRepository.save(juergen)
		authorRepository.save(peter)

		entityManager.flush()
		entityManager.clear()
	}

	@Test
	fun `Test get all authors`() {

		val testedAuthors = authorService.getAll()

		assertThat(testedAuthors).hasSize(2)
		assertThat(testedAuthors).contains(juergen)
		assertThat(testedAuthors).contains(peter)
	}

	@Test
	fun `Test get Juergen by id`() {

		val testedAuthor = juergen.id?.let { authorService.getById(it) }

		assertThat(testedAuthor).isEqualTo(juergen)

		try {
			authorService.getById(-1)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}
	}

	@Test
	fun `Test get Juergen by login`() {

		val testedAuthor = authorService.getByLogin(juergen.login)

		assertThat(testedAuthor).isEqualTo(juergen)

		try {
			authorService.getByLogin("failed login")
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}
	}

	@Test
	fun `Test create new author`() {

		assertThat(authorService.getAll()).hasSize(2)

		val newAuthor = Author("new login", "test firstname", "test lastname")
		val result = authorService.create(newAuthor)
		entityManager.flush()
		entityManager.clear()

		assertThat(authorService.getAll()).hasSize(3)
		assertThat(authorService.getByLogin(newAuthor.login)).isEqualTo(newAuthor)
		assertThat(result).isEqualTo(newAuthor)

		try {
			val newAuthorBis = Author("new login", "test firstname", "test lastname")
			authorService.create(newAuthorBis)
			entityManager.flush()
			entityManager.clear()
			fail("Insert same author twice")
		} catch (e: Exception) {
			logger.info("OK: Exception after inserting same author twice")
			logger.error(e.message)
			return
		}
	}

	@Test
	fun `Test remove Juergen by its id`() {

		assertThat(authorService.getAll()).hasSize(2)

		juergen.id?.let { authorService.remove(it) }

		try {
			authorService.remove(-1)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}

		assertThat(authorService.getAll()).hasSize(1)
		assertThat(authorService.getAll()).contains(peter)
	}

	@Test
	fun `Test remove Juergen by its login`() {

		assertThat(authorService.getAll()).hasSize(2)

		authorService.removeByLogin(juergen.login)

		try {
			authorService.removeByLogin("failed login")
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}

		assertThat(authorService.getAll()).hasSize(1)
		assertThat(authorService.getAll()).contains(peter)
	}

	@Test
	fun `Test update Juergen by its id`() {

		var updatedJuergen = juergen

		updatedJuergen.login = "updated login"
		updatedJuergen.firstName = "updated firstname"
		updatedJuergen.lastName = "updated lastname"

		val result = updatedJuergen.id?.let { authorService.update(it, updatedJuergen) }

		assertThat(authorService.getAll()).hasSize(2)
		assertThat(authorService.getByLogin(updatedJuergen.login)).isEqualTo(updatedJuergen)
		assertThat(result?.login).isEqualTo(updatedJuergen.login)

		try {
			authorService.update(-1, updatedJuergen)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}
	}

	@Test
	fun `Test update Juergen by its login`() {

		val updatedJuergen = Author(
			login = "updated login",
			firstName = "updated firstname",
			lastName = "updated lastname")

		val result = authorService.updateByLogin(juergen.login, updatedJuergen)

		assertThat(authorService.getAll()).hasSize(2)
		assertThat(authorService.getByLogin(updatedJuergen.login)).isEqualTo(updatedJuergen)
		assertThat(result).isEqualTo(updatedJuergen)

		try {
			authorService.update(-1, updatedJuergen)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}
	}
}
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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.server.ResponseStatusException
@ExtendWith(SpringExtension::class)
@DataJpaTest
@ActiveProfiles("test")
class AuthorServiceTests @Autowired constructor(
	val entityManager: TestEntityManager,
	val authorRepository: AuthorRepository,
) {

	private val logger: Logger = LogManager.getLogger(AuthorServiceTests::class.java)

	private val authorService = AuthorService(authorRepository)
	private val annie = Author("ae", "Annie", "Easley")
	private val mary = Author("mk", "Mary", "Keller")

	@BeforeEach
	fun init() {

		authorRepository.deleteAll()

		authorRepository.save(annie)
		authorRepository.save(mary)

		entityManager.flush()
		entityManager.clear()
	}

	@Test
	fun `Test get all authors`() {

		val testedAuthors = authorService.getAll()

		assertThat(testedAuthors).hasSize(2)
		assertThat(testedAuthors).contains(annie)
		assertThat(testedAuthors).contains(mary)
	}

	@Test
	fun `Test get Juergen by id`() {

		val testedAuthor = annie.id?.let { authorService.getById(it) }

		assertThat(testedAuthor).isEqualTo(annie)

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

		val testedAuthor = authorService.getByLogin(annie.login)

		assertThat(testedAuthor).isEqualTo(annie)

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

		val radia = Author("rp", "Radia", "Perlman")
		val result = authorService.create(radia)
		entityManager.flush()
		entityManager.clear()

		assertThat(authorService.getAll()).hasSize(3)
		assertThat(authorService.getByLogin(radia.login)).isEqualTo(radia)
		assertThat(result).isEqualTo(radia)

		try {
			val radiaBis = Author(radia.login, radia.firstName, radia.lastName)
			authorService.create(radiaBis)
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

		annie.id?.let { authorService.remove(it) }

		try {
			authorService.remove(-1)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}

		assertThat(authorService.getAll()).hasSize(1)
		assertThat(authorService.getAll()).contains(mary)
	}

	@Test
	fun `Test remove Juergen by its login`() {

		assertThat(authorService.getAll()).hasSize(2)

		authorService.removeByLogin(annie.login)

		try {
			authorService.removeByLogin("failed login")
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}

		assertThat(authorService.getAll()).hasSize(1)
		assertThat(authorService.getAll()).contains(mary)
	}

	@Test
	fun `Test update Juergen by its id`() {

		var updatedAnnie = annie

		updatedAnnie.login = "ef"
		updatedAnnie.firstName = "Elizabeth"
		updatedAnnie.lastName = "Feinler"

		val result = updatedAnnie.id?.let { authorService.update(it, updatedAnnie) }

		assertThat(authorService.getAll()).hasSize(2)
		assertThat(authorService.getByLogin(updatedAnnie.login)).isEqualTo(updatedAnnie)
		assertThat(result?.login).isEqualTo(updatedAnnie.login)

		try {
			authorService.update(-1, updatedAnnie)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}
	}

	@Test
	fun `Test update Juergen by its login`() {

		val updatedAnnie = Author(
			login = "kj",
			firstName = "Katherine",
			lastName = "Johnson")

		val result = authorService.updateByLogin(annie.login, updatedAnnie)

		assertThat(authorService.getAll()).hasSize(2)
		assertThat(authorService.getByLogin(updatedAnnie.login)).isEqualTo(updatedAnnie)
		assertThat(result).isEqualTo(updatedAnnie)

		try {
			authorService.update(-1, updatedAnnie)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}
	}
}
package com.pinattsu.repository

import com.pinattsu.entity.Author
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class AuthorRepositoryTests @Autowired constructor(
	val entityManager: TestEntityManager,
	val authorRepository: AuthorRepository,
) {

	private val annie = Author("ae", "Annie", "Easley")

	@BeforeEach
	fun init() {
		entityManager.clear()
		entityManager.persist(annie)
		entityManager.flush()
	}

	@Test
	fun `When findByLogin then return Annie`() {

		val testedAuthor = authorRepository.findByLogin(annie.login)
		assertThat(testedAuthor).isEqualTo(annie)
	}

	@Test
	fun `When findAll then return Annie`() {

		val testedAuthors = authorRepository.findAll()
		assertThat(testedAuthors).contains(annie)
		assertThat(testedAuthors).hasSize(1)
	}
}
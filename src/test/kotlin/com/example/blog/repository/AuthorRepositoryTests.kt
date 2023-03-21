package com.example.blog.reposotory

import com.example.blog.entity.Author
import com.example.blog.repository.AuthorRepository
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

	private val juergen = Author("springjuergen", "Juergen", "Hoeller")

	@BeforeEach
	fun init() {
		entityManager.clear()
		entityManager.persist(juergen)
		entityManager.flush()
	}

	@Test
	fun `When findByLogin then return Juergen`() {

		val testedAuthor = authorRepository.findByLogin(juergen.login)
		assertThat(testedAuthor).isEqualTo(juergen)
	}

	@Test
	fun `When findAll then return Juergen`() {

		val testedAuthors = authorRepository.findAll()
		assertThat(testedAuthors).contains(juergen)
		assertThat(testedAuthors).hasSize(1)
	}
}
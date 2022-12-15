package com.example.blog.reposotory

import com.example.blog.entity.Author
import com.example.blog.repository.AuthorRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class AuthorRepositoryTests @Autowired constructor(
	val entityManager: TestEntityManager,
	val authorRepository: AuthorRepository,
) {

	@Test
	fun `When findByLogin then return User`() {
		val juergen = Author("springjuergen", "Juergen", "Hoeller")

		val juergenBis = Author("springjuergencdqsdsq", "Juergendqsd", "Hoellerdqsdqs")

		entityManager.persist(juergen)
		entityManager.flush()
		val testedAuthor = authorRepository.findByLogin(juergen.login)

		assertThat(testedAuthor).isEqualTo(juergen)
	}
}
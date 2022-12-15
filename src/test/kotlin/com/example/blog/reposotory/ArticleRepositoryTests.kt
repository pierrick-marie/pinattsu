package com.example.blog.reposotory

import com.example.blog.entity.Article
import com.example.blog.entity.Author
import com.example.blog.repository.ArticleRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class ArticleRepositoryTests @Autowired constructor(
	val entityManager: TestEntityManager,
	val articleRepository: ArticleRepository,
) {
	private val juergen = Author("springjuergen", "Juergen", "Hoeller")

	private val primeArticle = Article(
		title = "Test title 0",
		content ="Test content 0",
		date = "2022-12-01",
		author = juergen)

	private val secondArticle = Article(
		title = "Test title 1",
		content ="Test content 1",
		date = "2022-12-02")

	@BeforeEach
	fun init() {
		entityManager.clear()
		entityManager.persist(juergen)
		entityManager.persist(primeArticle)
		entityManager.persist(secondArticle)
		entityManager.flush()
	}

	@Test
	fun `When findByLogin then return Articles`() {

		val testedArticles = articleRepository.findByAuthor(juergen)

		assertThat(testedArticles).hasSize(1)
		assertThat(testedArticles).contains(primeArticle)
	}

	@Test
	fun `When findByDate then return Articles`() {

		val testedArticles = articleRepository.findByDate("2022-12-01")

		assertThat(testedArticles).hasSize(1)
		assertThat(testedArticles).contains(primeArticle)
	}

	@Test
	fun `When findAllByOrderByDateDesc then return Articles`() {

		val testedArticles = articleRepository.findAllByOrderByDateDesc()

		assertThat(testedArticles).hasSize(2)

		assertThat(testedArticles).contains(primeArticle)
		assertThat(testedArticles).contains(secondArticle)

		val testedPrimeArticle = testedArticles.last();
		val testedSecondArticle = testedArticles.first();

		assertThat(testedPrimeArticle).isEqualTo(primeArticle)
		assertThat(testedSecondArticle).isEqualTo(secondArticle)
	}
}
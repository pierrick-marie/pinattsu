package com.example.blog.reposotory

import com.example.blog.entity.Article
import com.example.blog.entity.Author
import com.example.blog.repository.ArticleRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class ArticleRepositoryTests @Autowired constructor(
	val entityManager: TestEntityManager,
	val articleRepository: ArticleRepository,
) {

	@Test
	fun `When findByLogin then return Articles`() {
		val juergen = Author("springjuergen", "Juergen", "Hoeller")
		val primeArticle = Article(
			title = "Test title",
			content ="Test content",
			author = juergen)
		entityManager.persist(juergen)
		entityManager.persist(primeArticle)
		entityManager.flush()
		val testedArticles = articleRepository.findByAuthor(juergen)

		assertThat(testedArticles).hasSize(1)
		assertThat(testedArticles).contains(primeArticle)
	}

	@Test
	fun `When findByDate then return Articles`() {
		val primeArticle = Article(
			title = "Test title",
			content ="Test content",)
		val today = primeArticle.date
		entityManager.persist(primeArticle)
		entityManager.flush()
		val testedArticles = articleRepository.findByDate(today)

		assertThat(testedArticles).hasSize(1)
		assertThat(testedArticles).contains(primeArticle)
	}

	@Test
	fun `When findAllByOrderByDateDesc then return Articles`() {
		val primeArticle = Article(
			title = "Test title 0",
			content ="Test content 0",
			date = "2022-12-01")
		val secondArticle = Article(
			title = "Test title 1",
			content ="Test content 1",
			date = "2022-12-02")
		entityManager.persist(primeArticle)
		entityManager.persist(secondArticle)
		entityManager.flush()
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
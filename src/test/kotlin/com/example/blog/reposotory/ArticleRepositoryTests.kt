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
		assertEquals(testedArticles.size, 1)
		assertTrue(testedArticles.contains(primeArticle))
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
		assertEquals(testedArticles.size, 1)
		assertTrue(testedArticles.contains(primeArticle))
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
		assertTrue(testedArticles.contains(primeArticle))
		assertTrue(testedArticles.contains(secondArticle))

		assertEquals(testedArticles.size, 2)

		val testedPrimeArticle = testedArticles.get(1);
		val testedSecondArticle = testedArticles.get(0);

		assertTrue(testedPrimeArticle.equals(primeArticle))
		assertTrue(testedSecondArticle.equals(secondArticle))
	}
}
package com.pinattsu.repository

import com.pinattsu.entity.Article
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
class ArticleRepositoryTests @Autowired constructor(
	val entityManager: TestEntityManager,
	val articleRepository: ArticleRepository,
	val authorRepository: AuthorRepository,
) {

	private val annie = Author("ae", "Annie", "Easley")
	private val mary = Author("mk", "Mary", "Keller")

	private val primeArticle = Article(
		title = "Test title 0",
		content ="Test content 0",
		date = "2022-12-01",
		author = annie)

	private val secondArticle = Article(
		title = "Test title 1",
		content ="Test content 1",
		date = "2022-12-02",
		author = mary)

	@BeforeEach
	fun init() {
		authorRepository.deleteAll()
		articleRepository.deleteAll()

		authorRepository.save(annie)
		authorRepository.save(mary)
		articleRepository.save(primeArticle)
		articleRepository.save(secondArticle)

		entityManager.flush()
		entityManager.clear()
	}

	@Test
	fun `Delete author with its associated articles`() {

		var articles = articleRepository.findAll()
		var authors = authorRepository.findAll()

		assertThat(articles).hasSize(2)
		assertThat(authors).hasSize(2)

		annie.id?.let {authorRepository.deleteById(it) }
		entityManager.flush()
		entityManager.clear()

		articles = articleRepository.findAll()
		authors = authorRepository.findAll()

		assertThat(articles).hasSize(1)
		assertThat(authors).hasSize(1)
	}

	@Test
	fun `When findByLogin then return Articles`() {

		val testedArticles = articleRepository.findByAuthor(annie)

		assertThat(testedArticles).hasSize(1)
		assertThat(testedArticles.get(0).id).isEqualTo(primeArticle.id)
	}

	@Test
	fun `When findByDate then return Articles`() {

		val testedArticles = articleRepository.findByDate("2022-12-01")

		assertThat(testedArticles).hasSize(1)
		assertThat(testedArticles.get(0).id).isEqualTo(primeArticle.id)
	}

	@Test
	fun `When findAllByOrderByDateDesc then return Articles`() {

		val testedArticles = articleRepository.findAllByOrderByDateDesc()

		assertThat(testedArticles).hasSize(2)

		val testedPrimeArticle = testedArticles.last();
		val testedSecondArticle = testedArticles.first();

		assertThat(testedPrimeArticle.id).isEqualTo(primeArticle.id)
		assertThat(testedSecondArticle.id).isEqualTo(secondArticle.id)
	}
}
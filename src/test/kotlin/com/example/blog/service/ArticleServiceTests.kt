package com.example.blog.service

import com.example.blog.entity.Article
import com.example.blog.entity.Author
import com.example.blog.repository.ArticleRepository
import com.example.blog.repository.AuthorRepository
import com.ninjasquad.springmockk.clear
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
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.server.ResponseStatusException

@ExtendWith(SpringExtension::class)
@DataJpaTest
class ArticleServiceTests @Autowired constructor(
	val entityManager: TestEntityManager,
	val authorRepository: AuthorRepository,
	val articleRepository: ArticleRepository,
) {

	private val logger: Logger = LogManager.getLogger(ArticleServiceTests::class.java)

	private val authorService = AuthorService(authorRepository)
	private val articleService = ArticleService(articleRepository, authorService)

	private val juergen = Author("springjuergen", "Juergen", "Hoeller")
	private val peter = Author("peter", "Peter", "M.")

	private val primeArticle = Article(
		title = "Test title 0",
		content ="Test content 0",
		date = "2022-12-01",
		author = juergen)

	private val secondArticle = Article(
		title = "Test title 1",
		content ="Test content 1",
		date = "2022-12-02",
		author = peter)

	@BeforeEach
	fun init() {

		authorRepository.deleteAll()
		articleRepository.deleteAll()

		authorRepository.save(juergen)
		authorRepository.save(peter)
		articleRepository.save(primeArticle)
		articleRepository.save(secondArticle)

		entityManager.flush()
		entityManager.clear()
	}

	@Test
	fun `Test get all articles`() {

		val testedArticles = articleService.getAll()

		assertThat(testedArticles).hasSize(2)
		assertThat(testedArticles).contains(primeArticle)
		assertThat(testedArticles).contains(secondArticle)
	}

	@Test
	fun `Test get prime article by id`() {

		val testedArticle = primeArticle.id?.let { articleService.getById(it) }

		assertThat(testedArticle).isEqualTo(primeArticle)

		try {
			articleService.getById(-1)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}
	}

	@Test
	fun `Test create new article`() {

		val testedArticle = Article(
			title = "New tested article",
			content ="Exclusive content",
			date = "2022-12-03",
			author = peter)

		val newArticle = articleService.create(testedArticle)
		assertThat(newArticle.title).isEqualTo(testedArticle.title)

		val allArticles = articleService.getAll()
		assertThat(allArticles).hasSize(3)
		assertThat(allArticles).contains(newArticle)
	}

	@Test
	fun `Test remove prime article by its id`() {

		assertThat(articleService.getAll()).hasSize(2)

		primeArticle.id?.let {articleService.removeById(it)}

		assertThat(articleService.getAll()).hasSize(1)
		assertThat(articleService.getAll()).contains(secondArticle)

		try {
			articleService.removeById(-1)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}
	}

	@Test
	fun `Test remove author and its articles by cascade`() {
		assertThat(articleService.getAll()).hasSize(2)
		assertThat(authorService.getAll()).hasSize(2)

		juergen.id?.let { authorService.remove(it) }

		entityManager.flush()
		entityManager.clear()

		assertThat(articleService.getAll()).hasSize(1)
		assertThat(authorService.getAll()).hasSize(1)
	}

	@Test
	fun `Test get article by date`() {
		val articles = articleService.getByDate(primeArticle.date)

		assertThat(articles).hasSize(1)
		assertThat(articles).contains(primeArticle)
	}

	@Test
	fun `Test get article by author Juergen`() {
		val articles = articleService.getByAuthor(juergen)

		assertThat(articles).hasSize(1)
		assertThat(articles).contains(primeArticle)
	}

	@Test
	fun `Test create article with unknown author`() {
		val testedAuthor = Author(
			login = "Unknown",
			firstName = "F.",
			lastName = "L."
		)
		val testedArticle = Article(
			title = "New tested article",
			content ="Exclusive content",
			date = "2022-12-03",
			author = testedAuthor)

		try {
			articleService.create(testedArticle)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
		} catch (e: Exception) {
			fail(e.message)
		}
	}

	@Test
	fun `Test update prime article by its id`() {

		var updatedArticle = primeArticle

		updatedArticle.title = "Updated title"
		updatedArticle.content = "Exclusive content"

		val result = primeArticle.id?.let { articleService.updateById(it, updatedArticle) }

		assertThat(articleService.getAll()).hasSize(2)
		assertThat(result).isEqualTo(updatedArticle)

		try {
			articleService.updateById(-1, updatedArticle)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}
	}
}
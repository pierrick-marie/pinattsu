package com.example.blog.service

import com.example.blog.entity.Article
import com.example.blog.entity.Author
import com.example.blog.repository.ArticleRepository
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
class ArticleServiceTests @Autowired constructor(
	val entityManager: TestEntityManager,
	val authorRepository: AuthorRepository,
	val articleRepository: ArticleRepository,
) {

	private val logger: Logger = LogManager.getLogger(ArticleServiceTests::class.java)

	private val authorService = AuthorService(authorRepository)
	private val articleService = ArticleService(articleRepository, authorService)

	private val annie = Author("ae", "Annie", "Easley")
	private val mary = Author("mk", "Mary", "Keller")

	private val biographyOfAnnie = Article(
		title = "Annies's Biography",
		content = """
			Annie was born in 1933 in Birmingham Alabama, and died in 2011. She attended Xavier University where she majored in pharmacy for around 2 years. Shortly after finishing University, she met her husband and they moved to Cleveland. This is where Annie’s life changed for the better. As there was no pharmaceutical school nearby, she applied for a job at the National Advisory Committee for Aeronautics (NACA) and within 2 weeks had started working there. She was one of four African Americans who worked there and developed and implemented code which led to the development of the batteries used in hybrid cars. She is well known for being one of the famous women in technology for encouraging women and people of her colour to study and enter STEM fields.
		""".trimIndent(),
		date = "2022-12-01",
		author = annie)

	private val biographyOfMary = Article(
		title = "Mary's Biography",
		content = """
			Mary Keller, an American Roman Catholic religious sister, was born in 1913 and died in 1985. In 1958 she started at the National Science Foundation workshop in the computer science department at Dartmouth College which at the time was an all-male school. She teamed up with 2 other scientists to develop the BASIC computer programming language. In 1965 Mary earned her PH.D in computer science from the University of Michigan. She went on to develop a computer science department in a catholic college for women called Clarke College. For 20 years she chaired the department where she was an advocate for women in computer science, and supported working mothers by encouraging them to bring their babies to class with them. She is known as one of the famous women in technology for being the first woman to receive a PH.D in computer science, and Clarke University (Clarke College) have established the Mary Keller Computer Science Scholarship in her honour.
		""".trimIndent(),
		date = "2022-12-02",
		author = mary)

	@BeforeEach
	fun init() {

		authorRepository.deleteAll()
		articleRepository.deleteAll()

		authorRepository.save(annie)
		authorRepository.save(mary)
		articleRepository.save(biographyOfAnnie)
		articleRepository.save(biographyOfMary)

		entityManager.flush()
		entityManager.clear()
	}

	@Test
	fun `Test get all articles`() {

		val testedArticles = articleService.getAll()

		assertThat(testedArticles).hasSize(2)
		assertThat(testedArticles).contains(biographyOfAnnie)
		assertThat(testedArticles).contains(biographyOfMary)
	}

	@Test
	fun `Test get prime article by id`() {

		val testedArticle = biographyOfAnnie.id?.let { articleService.getById(it) }

		assertThat(testedArticle).isEqualTo(biographyOfAnnie)

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

		val newBiographyOfMary = Article(
			title = "New biography of Mary",
			content ="She was a wonderful woman!",
			date = "2022-12-03",
			author = mary)

		val newArticle = articleService.create(newBiographyOfMary)
		assertThat(newArticle.title).isEqualTo(newBiographyOfMary.title)

		val allArticles = articleService.getAll()
		assertThat(allArticles).hasSize(3)
		assertThat(allArticles).contains(newArticle)
	}

	@Test
	fun `Test remove prime article by its id`() {

		assertThat(articleService.getAll()).hasSize(2)

		biographyOfAnnie.id?.let {articleService.removeById(it)}

		assertThat(articleService.getAll()).hasSize(1)
		assertThat(articleService.getAll()).contains(biographyOfMary)

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

		annie.id?.let { authorService.remove(it) }

		entityManager.flush()
		entityManager.clear()

		assertThat(articleService.getAll()).hasSize(1)
		assertThat(authorService.getAll()).hasSize(1)
	}

	@Test
	fun `Test get article by date`() {
		val articles = articleService.getByDate(biographyOfAnnie.date)

		assertThat(articles).hasSize(1)
		assertThat(articles).contains(biographyOfAnnie)
	}

	@Test
	fun `Test get article by author Juergen`() {
		val articles = articleService.getByAuthor(annie)

		assertThat(articles).hasSize(1)
		assertThat(articles).contains(biographyOfAnnie)
	}

	@Test
	fun `Test create article with unknown author`() {
		val katherine = Author(
			login = "kj",
			firstName = "Katherine",
			lastName = "Johnson"
		)
		val biographyOfKatherine = Article(
			title = "Katherine's Biography",
			content ="She was one of 3 black students to attend West Virginia’s graduate college...",
			date = "2022-12-03",
			author = katherine)

		try {
			articleService.create(biographyOfKatherine)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
		} catch (e: Exception) {
			fail(e.message)
		}
	}

	@Test
	fun `Test update prime article by its id`() {

		var newBiographyOfAnnie = biographyOfAnnie

		newBiographyOfAnnie.title = "Updated biography of Annie"
		newBiographyOfAnnie.content = "She was a wonderful woman!"

		val result = biographyOfAnnie.id?.let { articleService.updateById(it, newBiographyOfAnnie) }

		assertThat(articleService.getAll()).hasSize(2)
		assertThat(result).isEqualTo(newBiographyOfAnnie)

		try {
			articleService.updateById(-1, newBiographyOfAnnie)
			fail("Exception not triggered")
		} catch (e: ResponseStatusException) {
			assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		} catch (e: Exception) {
			fail(e.message)
		}
	}
}
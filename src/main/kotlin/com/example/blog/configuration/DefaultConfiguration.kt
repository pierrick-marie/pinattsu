package com.example.blog.configuration

import com.example.blog.entity.Article
import com.example.blog.entity.Author
import com.example.blog.repository.ArticleRepository
import com.example.blog.repository.AuthorRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DefaultConfiguration {

	@Bean
	fun init(authorRepository: AuthorRepository, articleRepository: ArticleRepository) = ApplicationRunner {

		val ae =
			Author(
				login = "ae",
				firstName = "Annie",
				lastName = "Easley",
				description = "She is well known for being one of the famous women in technology for encouraging women and people of her colour to study and enter STEM fields."
			)

		val mk =
			Author(
				login = "mk",
				firstName = "Mary",
				lastName = "Keller",
				description = "She was an American Roman Catholic religious sister. She was born in 1913 and died in 1985. She teamed up with 2 other scientists to develop the BASIC computer programming language."
			)


		val art1 =
			Article(
				title = "Biography",
				content = """
					Annie was born in 1933 in Birmingham Alabama, and died in 2011. She attended Xavier University where she majored in pharmacy for around 2 years. Shortly after finishing University, she met her husband and they moved to Cleveland. This is where Annie’s life changed for the better. As there was no pharmaceutical school nearby, she applied for a job at the National Advisory Committee for Aeronautics (NACA) and within 2 weeks had started working there. She was one of four African Americans who worked there and developed and implemented code which led to the development of the batteries used in hybrid cars. She is well known for being one of the famous women in technology for encouraging women and people of her colour to study and enter STEM fields.
				""".trimIndent(),
				author = ae,
			)

		val art2 =
			Article(
				title = "Lorem Ipsum",
				content = """
					Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
				""".trimIndent(),
				date = "2023-03-19",
				author = ae,
			)

		val art3 =
			Article(
				title = "Biography",
				content = """
					Mary Keller, an American Roman Catholic religious sister, was born in 1913 and died in 1985. In 1958 she started at the National Science Foundation workshop in the computer science department at Dartmouth College which at the time was an all-male school. She teamed up with 2 other scientists to develop the BASIC computer programming language. In 1965 Mary earned her PH.D in computer science from the University of Michigan. She went on to develop a computer science department in a catholic college for women called Clarke College. For 20 years she chaired the department where she was an advocate for women in computer science, and supported working mothers by encouraging them to bring their babies to class with them. She is known as one of the famous women in technology for being the first woman to receive a PH.D in computer science, and Clarke University (Clarke College) have established the Mary Keller Computer Science Scholarship in her honour.
				""".trimIndent(),
				author = mk,
			)

		authorRepository.save(ae)
		authorRepository.save(mk)

		articleRepository.save(art1)
		articleRepository.save(art2)
		articleRepository.save(art3)
	}
}
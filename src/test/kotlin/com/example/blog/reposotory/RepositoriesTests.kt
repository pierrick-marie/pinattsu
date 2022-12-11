package com.example.blog.reposotory

import com.example.blog.entity.Article
import com.example.blog.entity.ArticleRepository
import com.example.blog.entity.Author
import com.example.blog.entity.AuthorRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val authorRepository: AuthorRepository,
    val articleRepository: ArticleRepository
) {

    @Test
    fun `When findByIdOrNull then return Article`() {
        val juergen = Author("springjuergen", "Juergen", "Hoeller")
        entityManager.persist(juergen)
        val article = Article(
            title = "Spring Framework 5.0 goes GA",
            content = "Dear Spring community ...",
            author = juergen)
        entityManager.persist(article)
        entityManager.flush()
        val found = articleRepository.findByIdOrNull(article.id!!)
         assertThat(found).isEqualTo(article)
    }

    @Test
    fun `When findByLogin then return User`() {
        val juergen = Author("springjuergen", "Juergen", "Hoeller")
        entityManager.persist(juergen)
        entityManager.flush()
        val user = authorRepository.findByLogin(juergen.login)
        assertThat(user).isEqualTo(juergen)
    }
}
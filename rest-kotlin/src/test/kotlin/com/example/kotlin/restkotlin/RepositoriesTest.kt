package com.example.kotlin.restkotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager

@DataJpaTest
class RepositoriesTest @Autowired constructor(
    val entityManager: EntityManager,
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository
) {


    @Test
    fun userRepositoryTest() {
        // Arrange
        var testUser = User(
            login = "testLogin",
            firstName = "testFirstName",
            lastName = "testLastName",
            description = "testDescription"
        )
        entityManager.persist(testUser);

        // Act
        val foundUser: User? = userRepository.findByLogin("testLogin");

        // Assert
        assertThat(foundUser).isNotNull
        assertThat(foundUser?.firstName).isEqualTo("testFirstName");
    }

    @Test
    fun articleRepositoryTest() {
        // Arrange
        var testUser = User(
            login = "testLogin",
            firstName = "testFirstName",
            lastName = "testLastName",
            description = "testDescription"
        )
        var testArticle: Article = Article(
            title = "testTitle",
            headline = "testHeadLine",
            content = "testContent",
            author = testUser,
            slug = "testSlug"
        )
        entityManager.persist(testArticle);

        // Act
        val articles: List<Article> = articleRepository.findAllByOrderByAddedAtDesc();

        // Assert
        assertThat(articles).hasSize(1)
        assertThat(articles).contains(testArticle);
    }
}
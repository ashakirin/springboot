package com.example.kotlin.restkotlin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
class KotlinRestController(
    @Autowired var userRepository: UserRepository,
    @Autowired var articleRepository: ArticleRepository
) {

    @PostMapping("/write")
    fun createUser(@RequestBody userTO: UserTO): String {
        var user: User = User(login = userTO.login, firstName = userTO.firstName, lastName = userTO.lastName)
        userRepository.save(user)
        var article: Article =
            Article(title = "testTitle", headline = "testHeadline", author = user, content = "testContent", id = 1L)
        articleRepository.save(article)
        return "hello"
    }

    @GetMapping("/read")
    fun read(model: Model): String {
        model["title"] = "Blog"
        var user: User? = userRepository.findByLogin("testLogin")
        var articles: List<Article> = articleRepository.findAllByOrderByAddedAtDesc();
        var articleCollected: String = articles.stream()
            .map(Article::title)
            .collect(Collectors.joining(";"))
        return user?.firstName.orEmpty() + articleCollected;
    }

    @GetMapping("/")
    fun blog(model: Model): String {
        return "blog"
    }
}
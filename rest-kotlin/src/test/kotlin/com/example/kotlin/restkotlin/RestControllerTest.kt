package com.example.kotlin.restkotlin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@ExtendWith(SpringExtension::class)
@WebMvcTest(KotlinRestController::class)
class RestControllerTest @Autowired constructor(
    val mockMvc: MockMvc
) {

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var articleRepository: ArticleRepository

    @Test
    fun testRestControllerRead() {
        var user = User(lastName = "test", firstName = "test", login = "test")
        var article = Article(
            title = "headline",
            content = "test",
            headline = "test",
            author = user
        )
        `when`(userRepository.findByLogin(anyString())).thenReturn(user)
        `when`(articleRepository.findAllByOrderByAddedAtDesc()).thenReturn(listOf(article))
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/read"))
            .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.OK.value()))
            .andExpect(MockMvcResultMatchers.content().string("testheadline"))
    }

    @Test
    fun testRestControllerWrite() {

        var userTO: UserTO = UserTO(login = "test", firstName = "test", lastName = "test")
        var objectMapper = ObjectMapper()
         mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/write")
             .content(ObjectMapper().writeValueAsString(userTO))
             .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.OK.value()))
            .andExpect(MockMvcResultMatchers.content().string("hello"))
    }
}
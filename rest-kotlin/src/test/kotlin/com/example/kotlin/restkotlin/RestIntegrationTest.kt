package com.example.kotlin.restkotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.client.RestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestIntegrationTest(@Autowired var restTemplate: TestRestTemplate) {

//    @BeforeAll
//    fun setup() {
//        println("Before!!!")
//    }

    @LocalServerPort
    private lateinit var randomServerPort: Integer;

    @Test
    fun testRestResponse() {
        println("Post: {randomServerPort}")
        val entity = restTemplate.getForEntity<String>("/", String::class.java)
        assertEquals("blog", entity.body)
    }
}
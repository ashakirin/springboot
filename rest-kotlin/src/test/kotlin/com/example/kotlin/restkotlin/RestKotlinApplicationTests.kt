package com.example.kotlin.restkotlin

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
class RestKotlinApplicationTests @Autowired constructor(
    var testRestTemplate: TestRestTemplate
) {

    @Test
    fun contextLoads() {
    }

}

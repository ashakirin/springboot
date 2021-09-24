package com.example.kotlin.restkotlin

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class User (
    var login: String,
    var firstName: String,
    var lastName: String,
    var description: String? = null,
    @Id @GeneratedValue var id: Long? = null
        ) {
}

@Entity
class Article(
    var title: String,
    var headline: String,
    var content: String,
    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var author: User,
    var slug: String = "slug",
    var addedAt: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue var id: Long? = null) {
}
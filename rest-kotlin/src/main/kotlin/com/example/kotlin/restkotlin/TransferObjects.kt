package com.example.kotlin.restkotlin

class UserTO constructor(
    var login: String,
    var firstName: String,
    var lastName: String,
    var description: String? = null
) {
}
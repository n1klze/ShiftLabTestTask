package com.example.registrationscreen

data class User(
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val password: String,
) : java.io.Serializable

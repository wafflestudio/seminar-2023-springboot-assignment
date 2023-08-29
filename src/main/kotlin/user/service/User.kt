package com.wafflestudio.seminar.spring2023.user.service

data class User(
    val username: String,
    val image: String,
) {
    fun getAccessToken(): String {
        return username.reversed()
    }
}

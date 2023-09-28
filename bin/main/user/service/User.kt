package com.wafflestudio.seminar.spring2023.user.service

data class User(
    val id: Long,
    val username: String,
    val image: String,
) {
    fun getAccessToken(): String {
        return username.reversed()
    }
}

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authenticated
package com.wafflestudio.seminar.spring2023.user.service


interface UserService {
    fun signUp(username: String, password: String, image: String): User
    fun signIn(username: String, password: String): User
    fun authenticate(accessToken: String): User
}

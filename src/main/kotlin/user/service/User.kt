package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity

data class User(
    val username: String,
    val image: String,
) {
    fun getAccessToken(): String {
        return username.reversed()
    }
}


fun UserEntity.toUser(): User {
    return User(
        username = this.username,
        image = this.image,
    )
}

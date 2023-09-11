package com.wafflestudio.seminar.spring2023.user.repository

import com.wafflestudio.seminar.spring2023.user.service.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name= "users")
class  UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val username: String,
    val password: String,
    val image: String,
) {
    fun toUser() = User(this.username, this.image)
}

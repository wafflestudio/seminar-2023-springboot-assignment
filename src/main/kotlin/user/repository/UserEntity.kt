package com.wafflestudio.seminar.spring2023.user.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesEntity
import jakarta.persistence.*

@Entity(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val username: String,
    val password: String,
    val image: String,
)

package com.wafflestudio.seminar.spring2023.user.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesEntity
import com.wafflestudio.seminar.spring2023.user.service.User
import jakarta.persistence.*

@Entity(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    val username: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false)
    val image: String,
)


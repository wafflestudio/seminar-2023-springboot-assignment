package com.wafflestudio.seminar.spring2023.user.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val username: String,
    val password: String,
    val image: String,
    @OneToMany(mappedBy = "user")
    val likePlaylists: List<PlaylistLikesEntity>
)

package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "playlist_likes")
class PlaylistLikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val playlistId: Long,
    val userId: Long,
)

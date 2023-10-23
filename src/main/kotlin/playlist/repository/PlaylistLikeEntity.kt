package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import jakarta.persistence.*

@Entity(name = "playlist_likes")
class PlaylistLikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long = 0L,

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    val user: UserEntity,

    @ManyToOne
    @JoinColumn(name = "playlist_id",nullable = false)
    val playlist: PlaylistEntity,

    )

package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import jakarta.persistence.*

@Entity(name="playlist_like")
class PlaylistLikeEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne
    @JoinColumn(name="playlist_id")
    val playlist: PlaylistEntity,
    @ManyToOne
    @JoinColumn(name="user_id")
    val user: UserEntity
)
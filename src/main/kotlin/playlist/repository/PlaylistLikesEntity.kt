package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.Playlist
import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import jakarta.persistence.*

@Entity(name = "playlist_likes")
class PlaylistLikesEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    val playlist: PlaylistEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserEntity,
)
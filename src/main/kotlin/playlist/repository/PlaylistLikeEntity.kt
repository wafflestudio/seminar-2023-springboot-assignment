package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity(name = "playlist_likes")
class PlaylistLikeEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne // default FetchType.EAGER
    @JoinColumn(name = "playlist_id")
    val playlist: PlaylistEntity,
    @ManyToOne // default FetchType.EAGER
    @JoinColumn(name = "user_id")
    val user: UserEntity,
)
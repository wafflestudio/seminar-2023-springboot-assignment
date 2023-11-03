package com.wafflestudio.seminar.spring2023.playlist.repository

<<<<<<< HEAD
import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
=======
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
<<<<<<< HEAD
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "playlist_likes")
class PlaylistLikeEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = 0L,
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    val playlist: PlaylistEntity,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user:UserEntity
)
=======

@Entity(name = "playlist_likes")
class PlaylistLikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val playlistId: Long,
    val userId: Long,
)
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1

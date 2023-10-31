package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository

<<<<<<< HEAD
interface PlaylistLikeRepository : JpaRepository<PlaylistLikeEntity,Long>{

    fun findByPlaylistAndUserId(playlistEntity: PlaylistEntity, userId: Long) : PlaylistLikeEntity?
}
=======
interface PlaylistLikeRepository : JpaRepository<PlaylistLikeEntity, Long> {
    fun findByPlaylistIdAndUserId(playlistId: Long, userId: Long): PlaylistLikeEntity?
}
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1

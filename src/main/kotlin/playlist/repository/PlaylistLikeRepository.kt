package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface PlaylistLikeRepository: JpaRepository<PlaylistLikeEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(pl) > 0 THEN TRUE ELSE FALSE END FROM playlist_likes pl WHERE pl.playlist.id = :playlistId AND pl.user.id = :userId")
    fun existsByIdAndUserId(playlistId: Long, userId: Long): Boolean
}

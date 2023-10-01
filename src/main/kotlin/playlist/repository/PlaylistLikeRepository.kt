package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface PlaylistLikeRepository : JpaRepository<PlaylistLikesEntity, Long> {
    fun existsByPlaylistIdAndUserId(playlistId: Long, userId: Long): Boolean
    @Transactional
    @Modifying
    @Query("DELETE FROM playlist_likes pl WHERE pl.playlist.id = :playlistId AND pl.user.id = :userId")
    fun deleteByPlaylistIdAndUserId(playlistId: Long, userId: Long)
}

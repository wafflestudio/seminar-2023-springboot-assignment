package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistLikeRepository: JpaRepository<PlaylistLikeEntity, Long> {
    fun existsByPlaylistIdAndUserId(playlistId: Long, userId: Long): Boolean
    fun deleteByPlaylistIdAndUserId(playlistId: Long, userId: Long)
}
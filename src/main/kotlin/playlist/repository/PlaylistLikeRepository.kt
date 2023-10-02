package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistLikeRepository : JpaRepository<PlaylistLikeEntity, Long> {
    fun findByPlaylistIdAndUserId(playlistId: Long, userId: Long): PlaylistLikeEntity?
}
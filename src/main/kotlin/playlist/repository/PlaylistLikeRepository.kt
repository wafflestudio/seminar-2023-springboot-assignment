package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistLikeRepository : JpaRepository<PlaylistLikeEntity, Long> {
    // 플레이 리스트 조회
    fun existsByPlaylistIdAndUserId(playlistId: Long, userId: Long) : Boolean

    // 플레이 리스트 좋아요 취소
    @Transactional
    fun deleteByPlaylistIdAndUserId(playlistId: Long, userId: Long)
}
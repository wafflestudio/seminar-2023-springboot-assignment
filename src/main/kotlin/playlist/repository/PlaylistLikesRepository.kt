package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistLikesRepository : JpaRepository<PlaylistLikesEntity, Long> {
    fun findByPlaylistIdAndUserId(playlistId: Long, userId: Long): PlaylistLikesEntity?
}
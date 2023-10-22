package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface PlaylistViewRepository : JpaRepository<PlaylistViewEntity, Long> {
    fun findAllByPlaylistIdInAndCreatedAtAfter(
        playlistIds: List<Long>,
        after: LocalDateTime
    ) : List<PlaylistViewEntity>

    fun existsByPlaylistIdAndUserIdAndCreatedAtAfterAndCreatedAtBefore(
        playlistId: Long,
        userId: Long,
        after: LocalDateTime,
        before: LocalDateTime,
    ): Boolean
}

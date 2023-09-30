package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {
    @Query("SELECT p FROM playlists p LEFT JOIN FETCH p.playlistSongs WHERE p.id = :id")
    fun findByIdWithSongs(id: Long): PlaylistEntity?
}
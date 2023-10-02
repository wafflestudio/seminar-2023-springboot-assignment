package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {
    @Query("SELECT DISTINCT p FROM playlists p LEFT JOIN FETCH p.songs s LEFT JOIN FETCH s.album a LEFT JOIN FETCH s.artists ar WHERE p.id = :id")
    fun findPlaylistEntityById(id: Long): PlaylistEntity?
}

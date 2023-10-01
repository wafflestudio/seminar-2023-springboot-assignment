package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {
    @Query("SELECT pl FROM playlists pl LEFT JOIN FETCH pl.playlistSongs WHERE pl.id = :id")
    fun findByIdWithSongs(id: Long): PlaylistEntity?
}

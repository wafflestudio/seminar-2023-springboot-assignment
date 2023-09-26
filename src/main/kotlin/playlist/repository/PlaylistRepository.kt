package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {
    @Query("SELECT p FROM playlists p LEFT JOIN FETCH p.playlist_songs ps LEFT JOIN FETCH ps.song s LEFT JOIN FETCH s.album a LEFT JOIN FETCH a.artist WHERE p.id = :id")
    fun findPlaylistWithSongsById(id: Long): Optional<PlaylistEntity>

}
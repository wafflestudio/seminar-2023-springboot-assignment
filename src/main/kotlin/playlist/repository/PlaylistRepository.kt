package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.Playlist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface PlaylistRepository : JpaRepository<Playlist?, Long?> {
    @Query("SELECT p FROM playlists p LEFT JOIN FETCH p.songs WHERE p.id = :playlistId")
    fun findByIdWithSongs(@Param("playlistId") playlistId: Long?): PlaylistEntity?
}
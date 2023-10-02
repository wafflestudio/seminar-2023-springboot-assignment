package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {
    @Query("SELECT p FROM playlists p JOIN FETCH p.playlistGroup pg JOIN FETCH p.playlistSong ps JOIN FETCH ps.song s JOIN FETCH s.album a JOIN FETCH a.artist WHERE p.id = :id")
    fun findPlaylistEntityById(id: Long): PlaylistEntity?
}
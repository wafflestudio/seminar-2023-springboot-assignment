package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository: JpaRepository<PlaylistEntity, Long> {
    @Query("SELECT p FROM playlists p " +
            "LEFT JOIN FETCH p.playlistSongs ps " +
            "LEFT JOIN FETCH ps.song s " +
            "LEFT JOIN FETCH s.album a " +
            "LEFT JOIN FETCH a.artist " +
            "WHERE p.id = :id")
    fun findByIdWithJoinFetch(id: Long): PlaylistEntity?
}
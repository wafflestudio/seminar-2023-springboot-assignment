package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository: JpaRepository<PlaylistEntity, Long> {
    @EntityGraph(attributePaths = ["playlist_songs.song.song_artists"])
    @Query("SELECT s FROM playlists s LEFT JOIN FETCH s.playlist_songs sa LEFT JOIN FETCH sa.song sab LEFT JOIN FETCH sab.album WHERE s.id = :id")
    fun findByIdWithJoinFetch(id: Long): PlaylistEntity?
}
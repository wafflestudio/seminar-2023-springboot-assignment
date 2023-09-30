package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {
    @Query("""
        SELECT p FROM playlists p 
        JOIN FETCH p.songList s
        JOIN FETCH s.songArtistRelationshipList sa
        JOIN FETCH sa.artist ar
        JOIN FETCH s.album ab
        WHERE p.id = :id
    """)
    fun findByIdWithSongs(id: Long): PlaylistEntity?

    @Query("""
        SELECT p FROM playlists p
        JOIN FETCH p.playlistLikes pl
        JOIN FETCH pl.user u
        WHERE p.id = :id
    """)
    fun findByIdWithUsers(id: Long): PlaylistEntity?
}
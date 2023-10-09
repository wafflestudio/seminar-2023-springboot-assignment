package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {
    @Query(
        """
        SELECT p FROM playlists p
        LEFT JOIN FETCH p.playlistSongs ps
        LEFT JOIN FETCH ps.song s
        LEFT JOIN FETCH s.songArtists sa
        LEFT JOIN FETCH sa.artist
        LEFT JOIN FETCH s.album
        LEFT JOIN FETCH s.album.artist
        WHERE p.id = :id
    """
    )
    fun findByIdOrNull(id: Long): PlaylistEntity?
}

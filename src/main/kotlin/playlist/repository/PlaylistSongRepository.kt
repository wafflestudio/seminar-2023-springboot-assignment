package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistSongRepository : JpaRepository<PlaylistSongEntity, Long> {
    @Query("SELECT s FROM songs s JOIN FETCH s.songArtist sa JOIN FETCH sa.artist sar JOIN FETCH s.album a WHERE s.id in :songId")
    fun findSongsInPlaylist(songId: List<Long>): List<SongEntity>
}
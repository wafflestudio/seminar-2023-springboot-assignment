package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long>{
    @Query("SELECT s FROM playlist_songs s" +
            " JOIN FETCH s.songOfPlaylist " +
            "JOIN FETCH s.songOfPlaylist.song_artists sa " +
            " JOIN FETCH sa.artistOfSong" +
            " JOIN FETCH s.songOfPlaylist.album" +
            " WHERE s.playlistOfSong.id = :id ORDER BY s.songOfPlaylist.id")
    fun findSongs(id: Long) : List<PlaylistSongEntity>
}
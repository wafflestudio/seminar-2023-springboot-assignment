package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.Playlist
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
interface PlaylistSongsRepository : JpaRepository<PlaylistSongsEntity, Long> {
    @Query("SELECT a FROM playlist_songs a LEFT JOIN FETCH a.song LEFT JOIN FETCH a.playlist WHERE a.playlist = :playlistEntity")
    fun findByPlaylist(playlistEntity: PlaylistEntity) : List<PlaylistSongsEntity>
    @Query("SELECT a FROM playlist_songs a LEFT JOIN FETCH a.song LEFT JOIN FETCH a.playlist JOIN FETCH a.song.album JOIN FETCH a.song.songArtists JOIN FETCH a.song.album.artist WHERE a.playlist.id = :plid")
    fun findByPlaylistId(plid: Long) : List<PlaylistSongsEntity>
}
package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long>{
    @Query("SELECT DISTINCT a FROM playlists a "
            +"JOIN FETCH a.playlistSongs "
            +"JOIN FETCH a.playlistSongs.song "
            +"JOIN FETCH a.playlistSongs.song.songArtists "
            +"JOIN FETCH a.playlistSongs.song.songArtists.artist "
            +"JOIN FETCH a.playlistSongs.song.album "
            +"JOIN FETCH a.playlistSongs.song.album.artist "
            +"WHERE a.id = :plid")
    fun findByplId(plid : Long) : PlaylistEntity?
}
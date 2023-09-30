package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.Playlist
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
interface PlaylistSongsRepository : JpaRepository<PlaylistSongsEntity, Long> {
    @Query("SELECT a FROM playlist_songs a WHERE a.playlist = :playlistEntity")
    fun findByPlaylist(playlistEntity: PlaylistEntity) : Map<PlaylistEntity, List<SongEntity>>
}
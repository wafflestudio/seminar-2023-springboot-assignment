package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.Playlist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistLikeRepository : JpaRepository<PlaylistLikeEntity, Long> {
    //@Query("")
    fun findPlaylistLikeEntityByPlaylistId(playlistId: Long): List<PlaylistLikeEntity>
}
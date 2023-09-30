package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistLikesRepository : JpaRepository<PlaylistLikesEntity, Long> {
    fun findByPlaylist(playlistEntity: PlaylistEntity): List<PlaylistLikesEntity>
}
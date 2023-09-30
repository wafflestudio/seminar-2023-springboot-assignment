package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.Playlist
import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistLikeRepository: JpaRepository<PlaylistLikeEntity, Long> {
    fun existsByPlaylistAndUser(playlist: PlaylistEntity, user: UserEntity): Boolean
    fun deleteByPlaylistAndUser(playlist: PlaylistEntity, user: UserEntity)
}
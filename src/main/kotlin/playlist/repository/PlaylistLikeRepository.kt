package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistLikeRepository : JpaRepository<PlaylistLikeEntity, Long> {

    fun save(playlistLike: PlaylistLikeEntity)
    fun deleteByPlaylistIdAndUserId(playlistId: Long, userId: Long)

    fun existsByPlaylistIdAndUserId(playlistId: Long, userId: Long): Boolean
}
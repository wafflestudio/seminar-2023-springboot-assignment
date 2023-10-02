package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistLikesRepository: JpaRepository<PlaylistLikesEntity, Long> {
    @Query("select count(pl.id) > 0 from playlist_likes pl where pl.playlist.id=:playlistId and pl.user.id=:userId")
    fun existsByPlaylistIdAndUserId(playlistId: Long, userId: Long): Boolean

    @Query("select pl from playlist_likes pl where pl.playlist.id=:playlistId and pl.user.id=:userId")
    fun findByPlaylistIdAndUserId(playlistId: Long, userId: Long): PlaylistLikesEntity?
}
package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistLikesRepository: JpaRepository<PlaylistLikesEntity, Long> {
    @Query("SELECT p FROM playlist_likes p WHERE p.playlist_id = :playlistId and p.user_id = :userId")
    fun findUserPlaylistLike(playlistId: Long, userId: Long): PlaylistLikesEntity?
}

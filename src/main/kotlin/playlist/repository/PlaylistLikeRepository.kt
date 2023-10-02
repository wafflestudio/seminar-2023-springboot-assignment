package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
interface PlaylistLikeRepository : JpaRepository<PlaylistLikeEntity, Long> {
    @Query("SELECT a FROM playlist_likes a LEFT JOIN FETCH a.playlist JOIN FETCH a.user WHERE a.user.id = :uid AND a.playlist.id = :plid")
    fun findByUserIdAndPlaylistId(plid : Long, uid : Long) : PlaylistLikeEntity?
}
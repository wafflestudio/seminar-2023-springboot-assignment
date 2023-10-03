package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface PlaylistLikeRepository : JpaRepository<PlaylistLikeEntity, Long> {
    fun existsByPlaylistIdAndUserId(playlistId: Long, userId: Long): Boolean
    @Transactional //데이터 구획
    @Query("DELETE FROM playlist_likes pl WHERE pl.playlist.id = :playlistId AND pl.user.id = :userId")
    fun deleteByPlaylistIdAndUserId(playlistId: Long, userId: Long) //플레이리스트 아이디와 유저 아이디를 받아서 해당하는 좋아요 엔티티를 지워라

}
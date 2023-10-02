package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface PlaylistLikeRepository: JpaRepository<PlaylistLikeEntity, Long> {

    fun findByPlaylistIdAndUserId(playlistId:Long, userId:Long):PlaylistLikeEntity?

    @Transactional
    fun deleteByPlaylistIdAndUserId(playlistId:Long, userId:Long)

    @Transactional
    fun save(playlistLikeEntity: PlaylistLikeEntity)
}
package com.wafflestudio.seminar.spring2023.customplaylist.repository

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface CustomPlaylistRepository : JpaRepository<CustomPlaylistEntity, Long> {
    fun findAllByUserId(userId: Long): List<CustomPlaylistEntity>

    fun findByIdAndUserId(id: Long, userId: Long): CustomPlaylistEntity?

    fun countByUserId(userId: Long): Long

    @Query(
        """
            SELECT ps FROM custom_playlists ps
                JOIN FETCH ps.songs pss
                WHERE ps.id = :id AND ps.userId = :userId
        """
    )
    fun findByIdWithSongs(id: Long, userId: Long): CustomPlaylistEntity

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cp FROM custom_playlists cp WHERE cp.id = :id AND cp.userId = :userId")
    fun findByIdAndUserIdForUpdate(id: Long, userId: Long): CustomPlaylistEntity?
}

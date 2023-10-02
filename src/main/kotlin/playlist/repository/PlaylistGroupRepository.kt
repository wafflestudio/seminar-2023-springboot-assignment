package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository : JpaRepository<PlaylistGroupEntity, Long> {
    @Query("SELECT p FROM playlist_groups p inner JOIN FETCH p.playlists WHERE p.open = true")
    fun findAllOpenWithJoinFetch(): List<PlaylistGroupEntity>
}
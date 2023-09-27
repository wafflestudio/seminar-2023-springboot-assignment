package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository : JpaRepository<PlaylistGroupEntity, Long> {
    @Query("SELECT pg FROM playlist_groups pg LEFT JOIN FETCH pg.playlists WHERE pg.open = true")
    fun findAllOpenWithJoinFetch(): List<PlaylistGroupEntity>
}
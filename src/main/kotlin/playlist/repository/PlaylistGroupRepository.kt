package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository : JpaRepository<PlaylistGroupEntity, Long> {
    @Query("SELECT pg FROM playlist_groups pg JOIN FETCH pg.playlist p WHERE pg.open = :open")
    fun findPlaylistGroupEntityByOpen(open: Boolean): List<PlaylistGroupEntity>
}
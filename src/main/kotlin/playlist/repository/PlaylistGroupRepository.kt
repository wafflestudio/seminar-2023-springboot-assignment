package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository: JpaRepository<PlaylistGroupEntity, Long> {
    @Query("select a from playlist_groups a inner join fetch a.playlists where a.open = :open")
    fun findNotEmptyGroupsByOpen(open: Boolean): List<PlaylistGroupEntity>
}
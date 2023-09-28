package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository : JpaRepository<PlaylistGroupEntity, Long> {
    @Query("SELECT g FROM playlist_groups g JOIN FETCH g.playlists p WHERE g.open = true AND SIZE(g.playlists) > 0")
    fun findOpenGroups(): List<PlaylistGroupEntity>
}
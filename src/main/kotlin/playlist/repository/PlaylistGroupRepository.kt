package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository: JpaRepository<PlaylistGroupEntity, Long> {
    @Query("SELECT DISTINCT s FROM playlist_groups s LEFT JOIN FETCH s.playlists WHERE s.open = true")
    fun findByOpenTrueWithJoinFetch(): List<PlaylistGroupEntity>
}
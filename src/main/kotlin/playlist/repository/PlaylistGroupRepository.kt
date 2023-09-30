package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository: JpaRepository<PlaylistGroupEntity, Long> {
    @Query("SELECT g FROM playlist_groups g LEFT JOIN FETCH g.playlists p WHERE g.open = :isOpen")
    fun findByOpenWithJoinFetch(isOpen: Boolean): List<PlaylistGroupEntity>
}
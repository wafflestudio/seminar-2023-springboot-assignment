package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
interface PlaylistGroupRepository : JpaRepository<PlaylistGroupEntity, Long> {
    @Query("SELECT a FROM playlist_groups a LEFT JOIN FETCH a.playlist WHERE a.open = true AND a.playlist IS NOT EMPTY ")
    fun findByOpen() : List<PlaylistGroupEntity>
}
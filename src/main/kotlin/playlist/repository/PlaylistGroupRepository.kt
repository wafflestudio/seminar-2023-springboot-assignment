package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository :JpaRepository<PlaylistGroupEntity,Long>{
    @Query("SELECT p FROM playlist_groups p LEFT JOIN FETCH p.playlists WHERE p.open = :open")
    fun findByOpenWithJoinFetch(open:Boolean):List<PlaylistGroupEntity>
}
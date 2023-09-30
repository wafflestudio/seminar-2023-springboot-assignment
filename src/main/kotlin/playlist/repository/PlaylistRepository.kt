package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long>{
    @Query("SELECT a FROM playlists a WHERE a.id = :plid ")
    fun findByplId(plid : Long) : PlaylistEntity?
}
package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("SELECT DISTINCT s FROM songs s LEFT JOIN FETCH s.album a LEFT JOIN FETCH s.artists ar WHERE s.title LIKE %:keyword%")
    fun findSongsByTitleContaining(keyword: String): List<SongEntity>
}
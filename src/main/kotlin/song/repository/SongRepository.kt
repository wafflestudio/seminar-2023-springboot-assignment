package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("SELECT DISTINCT s FROM songs s LEFT JOIN FETCH s.song_artists sa LEFT JOIN FETCH sa.artist LEFT JOIN FETCH s.album WHERE s.title LIKE %:keyword%")
    fun findByTitleContainingWithJoinFetch(@Param("keyword") keyword: String): List<SongEntity>
}
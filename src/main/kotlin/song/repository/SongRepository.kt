package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("SELECT s FROM songs s " +
            "INNER JOIN FETCH s.album a " +
            "INNER JOIN FETCH s.songArtists sa " +
            "INNER JOIN FETCH sa.artist " +
            "WHERE s.title LIKE %:keyword% " +
            "ORDER BY LENGTH(s.title)")
    fun searchByTitleOrderByTitleLength(keyword: String): List<SongEntity>
}
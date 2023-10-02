package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.songArtists sa LEFT JOIN FETCH sa.artist WHERE s.id IN :songIds")
    fun findBySongIdWithJoinFetch(songIds: List<Long>): List<SongEntity>

    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.album a LEFT JOIN FETCH s.songArtists sa LEFT JOIN FETCH sa.artist aa WHERE s.title LIKE %:keyword% ORDER BY LENGTH(s.title)")
    fun searchWithJoinFetch(keyword: String) : List<SongEntity>
}
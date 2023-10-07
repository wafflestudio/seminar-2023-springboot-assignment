package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query(
        """SELECT s FROM songs s 
            LEFT JOIN FETCH s.album 
            LEFT JOIN FETCH s.artists ar
            LEFT JOIN FETCH ar.artist
            WHERE s.title LIKE %:title% 
            ORDER BY LENGTH(s.title) ASC"""
    )
    fun findByTitleContainingOrderByTitleLengthAsc(title: String): List<SongEntity>
}

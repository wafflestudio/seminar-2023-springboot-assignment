package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("""
        SELECT s FROM songs s
        JOIN FETCH s.album ab
        JOIN FETCH s.songArtistRelationshipList sar
        JOIN FETCH sar.artist ar
        WHERE s.title LIKE :term
    """)
    fun findTitleLike(term: String): List<SongEntity>
}
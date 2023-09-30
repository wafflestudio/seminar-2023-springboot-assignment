package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    @Query("""
        SELECT ab FROM albums ab
        JOIN FETCH ab.artist ar
        WHERE ab.title LIKE :term
    """)
    fun findTitleLike(term: String) : List<AlbumEntity>
}
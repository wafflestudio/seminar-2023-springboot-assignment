package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    @Query(
        """
        SELECT a FROM albums a
        JOIN FETCH a.artist
        WHERE a.title LIKE %:keyword%
        """
    )
    fun findAllByTitleContainingWithJoinFetch(keyword: String): List<AlbumEntity>

    fun findByTitle(title: String): AlbumEntity?
}

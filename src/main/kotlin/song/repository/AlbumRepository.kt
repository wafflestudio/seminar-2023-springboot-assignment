package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    @Query("SELECT DISTINCT a FROM albums a LEFT JOIN FETCH a.artist ar WHERE a.title LIKE %:keyword%")
    fun findAlbumsByTitleContaining(keyword: String): List<AlbumEntity>
}
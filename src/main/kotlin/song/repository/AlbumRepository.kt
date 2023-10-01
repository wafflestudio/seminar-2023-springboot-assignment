package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Album
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    @Query("SELECT a FROM albums a LEFT JOIN FETCH a.artist " +
            "WHERE a.title LIKE CONCAT('%',:title,'%') ORDER BY length(a.title) ASC")
    fun findByTitleContaining(title: String): List<AlbumEntity>
}
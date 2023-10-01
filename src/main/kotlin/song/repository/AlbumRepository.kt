package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
//    @Query("SELECT a FROM albums a WHERE a.title LIKE %:keyword%")
//    fun findByTitleContaining(@Param("keyword") keyword: String): List<AlbumEntity>
//    @EntityGraph(attributePaths = arrayOf("artist"))

    @Query("SELECT a FROM albums a JOIN FETCH a.artist WHERE a.title LIKE %:keyword%")
    fun findByTitleContaining(keyword: String): List<AlbumEntity>

}
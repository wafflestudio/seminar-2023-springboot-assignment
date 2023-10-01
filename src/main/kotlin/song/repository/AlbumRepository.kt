package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AlbumRepository : JpaRepository<AlbumEntity, Long>{
    // 아티스트 없는 앨범들이 있을까? various artist도 artist?
    @Query("SELECT a FROM albums a INNER JOIN FETCH a.artist WHERE a.title LIKE %:keyword%")
    fun findByTitleContainingWithArtist(keyword: String): List<AlbumEntity>
}
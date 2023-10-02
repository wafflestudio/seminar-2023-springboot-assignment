package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AlbumRepository : JpaRepository<AlbumEntity, Long>{
    @Query(
        "SELECT a FROM albums a LEFT JOIN FETCH a.artist WHERE a.title LIKE :keyword ORDER BY LENGTH(a.title) ")

    fun searchSongsTWithFetchJoin(keyword: String): List<AlbumEntity>

}
package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AlbumRepository : JpaRepository<AlbumEntity, Long>{
    @Query("SELECT a FROM albums a " +
            "LEFT join FETCH a.artist " +
            "where a.title LIKE %:keyword% order by LENGTH(a.title)")
    fun findAllByTitleContainsKeywordOrderByTitle(keyword : String) :List<AlbumEntity>

}
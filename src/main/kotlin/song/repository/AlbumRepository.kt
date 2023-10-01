package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Album
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    @Query("SELECT a FROM albums AS a " +
            "LEFT JOIN FETCH a.artist " +
            "WHERE a.title like %:keyword% " +
            "ORDER BY length(a.title) ASC ")
    fun findAllAlbumsByTitleWithJoinFetch(keyword: String): List<AlbumEntity>
}
package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("SELECT a FROM songs a LEFT JOIN FETCH a.album JOIN FETCH a.songArtists JOIN FETCH a.album.artist JOIN FETCH a.songArtists.artist WHERE a.title like %:keyword%")
    fun findByTitleContaining(keyword : String) : List<SongEntity>
}
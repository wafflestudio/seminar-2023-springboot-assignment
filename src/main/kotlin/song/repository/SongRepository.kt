package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query(
        "SELECT s FROM songs s " +
                "LEFT JOIN FETCH s.album a" +
                "LEFT JOIN FETCH s.songArtists ar " +
                "LEFT JOIN FETCH ar.artist WHERE s.title LIKE '%:keyword%'"
    )
    fun searchSongTWithFetchJoin(@Param("keyword") keyword: String): List<SongEntity>

    ////check
    @Query(
        "SELECT DISTINCT s FROM songs s " +
                "LEFT JOIN FETCH s.album a " +
                "LEFT JOIN FETCH s.songArtists ar " +
                "LEFT JOIN FETCH ar.artist" +
                "WHERE s.id IN: ids"
    )
    fun searchSongIWithFetchJoin(@Param("keyword") keyword: String): List<SongEntity>
}
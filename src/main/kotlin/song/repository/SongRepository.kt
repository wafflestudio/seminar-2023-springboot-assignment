package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query(
            "SELECT s FROM songs s " +
                    "LEFT JOIN FETCH s.album a " +
                    "LEFT JOIN FETCH s.artists ar " +
                    //
                    "WHERE s.title LIKE '%:keyword%'"
    )
    fun searchSongsWithFetchJoin(@Param("keyword") keyword: String): List<SongEntity>

////check
    @Query(
            "SELECT DISTINCT a FROM albums a " +
                    "LEFT JOIN FETCH a.songs s " +
                    "LEFT JOIN FETCH a.artist ar " +
                    "WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    fun searchAlbumsWithFetchJoin(@Param("keyword") keyword: String): List<AlbumEntity>
}
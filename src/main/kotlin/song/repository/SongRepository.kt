package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.album LEFT JOIN FETCH s.songArtists sa LEFT JOIN FETCH sa.artist WHERE s.title LIKE %:keyword% ORDER BY LENGTH(s.title) ")
    fun findByTitleContainingWithJoinFetch(keyword: String): List<SongEntity>

    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.album LEFT JOIN FETCH s.songArtists sa LEFT JOIN FETCH sa.artist WHERE s.id IN :ids")
    fun findByIdsWithJoinFetch(@Param("ids") ids: List<Long>): List<SongEntity>
}
package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SongRepository : JpaRepository<SongEntity, Long> {

    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.album al LEFT JOIN FETCH s.artists sa LEFT JOIN FETCH sa.artist a WHERE s.id IN :songs")
    fun searchSongIWithFetchJoin(songs: List<Long>): List<SongEntity>

    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.album al LEFT JOIN FETCH s.artists sa LEFT JOIN FETCH sa.artist a WHERE s.title LIKE :keyword ORDER BY LENGTH(s.title)")
    fun searchSongTWithFetchJoin(keyword: String): List<SongEntity>

}
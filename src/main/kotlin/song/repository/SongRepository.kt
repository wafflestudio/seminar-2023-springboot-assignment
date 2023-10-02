package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("SELECT s FROM songs s " +
            "LEFT JOIN FETCH s.album " +
            "LEFT JOIN FETCH s.song_artists sa " +
            "LEFT JOIN FETCH sa.artist " +
            "WHERE s.title LIKE CONCAT('%',:title,'%') " +
            "ORDER BY length(s.title) ASC")
    fun findByTitleContaining(title: String): List<SongEntity>
    @Query("SELECT s FROM songs s " +
            "LEFT JOIN FETCH s.album a " +
            "LEFT JOIN FETCH s.song_artists sa " +
            "LEFT JOIN FETCH sa.artist " +
            "WHERE s.id IN :idList")
    fun findAllByIDListJoinFetch(idList: List<Long>): List<SongEntity>
}
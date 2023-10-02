package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("SELECT s FROM songs s " +
            "LEFT JOIN FETCH s.songArtists sa " +
            "LEFT JOIN FETCH sa.artist " +
            "LEFT JOIN FETCH s.album " +
            "WHERE s.id IN :songIds")
    fun findByIdWithJoinFetch(songIds: List<Long>): List<SongEntity>
    @Query("SELECT s FROM songs s " +
            "LEFT JOIN FETCH s.album a " +
            "LEFT JOIN FETCH s.songArtists sa " +
            "LEFT JOIN FETCH sa.artist " +
            "WHERE s.title LIKE %:keyword%")
    fun findByTitleContainingWithJoinFetch(keyword: String): List<SongEntity>
}
package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("SELECT s FROM songs AS s " +
            "LEFT JOIN fetch s.album " +
            "LEFT JOIN FETCH s.song_artists AS sa " +
            "LEFT JOIN FETCH sa.artist " +
            "WHERE s.title LIKE %:keyword% " +
            "ORDER BY length(s.title) ASC ")
    fun findAllSongsByTitleWithJoinFetch(keyword: String): List<SongEntity>
    @Query("SELECT s FROM songs AS s " +
            "LEFT JOIN FETCH s.song_artists AS sa " +
            "LEFT JOIN FETCH sa.artist " +
            "LEFT JOIN FETCH s.album AS a " +
            "WHERE s.id in :idList")
    fun findAllSongsInPlaylist(idList: List<Long>): List<SongEntity>
}
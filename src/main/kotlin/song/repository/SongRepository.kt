package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("SELECT s FROM songs s JOIN FETCH s.songArtist sa JOIN FETCH sa.artist ar JOIN FETCH s.album a WHERE s.title LIKE %:titlePattern%")
    fun findSongByTitleContainsWithJoinFetch(titlePattern: String): List<SongEntity>
}
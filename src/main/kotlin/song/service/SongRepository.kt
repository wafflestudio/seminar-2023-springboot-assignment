package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {

    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.songArtists sa LEFT JOIN FETCH sa.artist LEFT JOIN FETCH s.album WHERE s.id IN :songIds")
    fun findBySongIdsWithJoinFetch(songIds: List<Long>): List<SongEntity>
}
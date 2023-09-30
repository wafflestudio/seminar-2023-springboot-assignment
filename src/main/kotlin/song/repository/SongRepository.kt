package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository: JpaRepository<SongEntity, Long> {
    @Query(
            "SELECT s FROM songs s " +
            "JOIN FETCH s.songArtists sa " +
            "LEFT JOIN FETCH sa.artist " +
            "WHERE s.id IN :idList"
    )
    fun findByIdInWithJoinFetch(idList: List<Long>): List<SongEntity>
}
package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArtistRepository : JpaRepository<ArtistEntity, Long> {
    @Query("SELECT a FROM artists a LEFT JOIN FETCH a.albums WHERE a.id = :id")
    fun findByIdWithJoinFetch(id: Long): ArtistEntity?

    @Query("SELECT a FROM artists a LEFT JOIN FETCH a.songArtists sa LEFT JOIN FETCH sa.song s WHERE s.id IN :songIds")
    fun findBySongIdWithJoinFetch(songIds: List<Long>): List<ArtistEntity>
}

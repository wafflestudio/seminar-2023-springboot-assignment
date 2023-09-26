package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongArtistRepository : JpaRepository<SongArtistEntity, Long> {
    @Query("SELECT sa FROM song_artists sa LEFT JOIN FETCH sa.song LEFT JOIN FETCH sa.artist WHERE sa.song IN :songIds")
    fun findAllWithArtistsAndAlbumByIds(songIds: List<Long>): List<SongEntity>
}
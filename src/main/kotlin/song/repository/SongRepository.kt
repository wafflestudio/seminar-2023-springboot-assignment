package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.song_artists sa LEFT JOIN FETCH sa.artist a LEFT JOIN FETCH s.album WHERE s.title LIKE %:keyword% ")
    fun findByTitleContaining(keyword: String): List<SongEntity>
//    fun findAllByIdIn(ids: List<Long>): List<SongEntity>
    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.song_artists sa LEFT JOIN FETCH sa.artist a WHERE s.id IN :songIds")
    fun findSongsWithArtistsAndAlbumByIds(songIds: List<Long>): List<SongEntity>
}

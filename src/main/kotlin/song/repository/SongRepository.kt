package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long>{
    @Query("SELECT s FROM songs s JOIN FETCH s.song_artists sa JOIN FETCH s.album JOIN FETCH sa.artistOfSong WHERE s.title LIKE %:keyword% order by length(s.title) ")
    fun findKeywordSong(keyword: String) : List<SongEntity>
}
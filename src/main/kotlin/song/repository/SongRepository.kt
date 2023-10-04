package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("""
        select s 
        from songs s 
        join fetch s.song_artists sa
        join fetch sa.artist
        join fetch s.album
        where s.title like %:keyword%
        order by length(s.title)
    """)
    fun findAllSongsByTitle(keyword: String): List<SongEntity>
}
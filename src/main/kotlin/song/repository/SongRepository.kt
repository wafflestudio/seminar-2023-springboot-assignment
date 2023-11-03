package com.wafflestudio.seminar.spring2023.song.repository
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity,Long> {

    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.album LEFT JOIN FETCH s.album.artist LEFT JOIN FETCH s.songArtists sa LEFT JOIN FETCH sa.artist a WHERE s.id IN :ids")
    fun findByIdInJoinFetch(ids:List<Long>) : List<SongEntity>
    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.album  LEFT JOIN FETCH s.songArtists sa LEFT JOIN FETCH sa.artist a WHERE s.title like :title")
    fun findByTitleLike(title:String) : List<SongEntity>
}
=======

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    @Query("""
        SELECT s FROM songs s
        JOIN FETCH s.album al 
        JOIN FETCH s.artists sa
        JOIN FETCH sa.artist
        WHERE s.title LIKE %:keyword%
    """)
    fun findAllByTitleContainingWithJoinFetch(keyword: String): List<SongEntity>

    @Query("""
        SELECT s FROM songs s
        JOIN FETCH s.album al 
        JOIN FETCH s.artists sa
        JOIN FETCH sa.artist
        WHERE s.id IN :ids
    """)
    fun findAllByIdWithJoinFetch(ids: List<Long>): List<SongEntity>
}
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1

package com.wafflestudio.seminar.spring2023.song.repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity,Long> {

    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.album LEFT JOIN FETCH s.album.artist LEFT JOIN FETCH s.songArtists sa LEFT JOIN FETCH sa.artist a WHERE s.id IN :ids")
    fun findByIdInJoinFetch(ids:List<Long>) : List<SongEntity>
    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.album  LEFT JOIN FETCH s.songArtists sa LEFT JOIN FETCH sa.artist a WHERE s.title like :title")
    fun findByTitleLike(title:String) : List<SongEntity>
}
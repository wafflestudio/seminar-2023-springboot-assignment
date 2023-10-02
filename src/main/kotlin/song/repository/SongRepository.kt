package com.wafflestudio.seminar.spring2023.song.repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository :JpaRepository<SongEntity, Long> {

    @Query("SELECT s FROM songs s LEFT JOIN FETCH s.album LEFT JOIN FETCH s.songArtists sa LEFT JOIN FETCH sa.artist WHERE s.title LIKE %:keyword% order by LENGTH(s.title)")
    fun findAllByTitleContainsOrderByTitle(keyword : String) : List<SongEntity>

    @Query("SELECT s from songs s left join fetch s.album left join fetch s.songArtists sa left join fetch sa.artist WHERE s.id IN :ids")
    fun findSongEntitiesByIdIn(ids : List<Long>) : List<SongEntity>
}
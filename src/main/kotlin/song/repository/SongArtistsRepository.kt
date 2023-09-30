package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongArtistsRepository : JpaRepository<SongArtistsEntity, Long> {

    @Query("SELECT a FROM songs_artists a JOIN FETCH a.song LEFT JOIN FETCH a.artist WHERE a.artist.id = :artist_id")
    fun findByArtistId(artist_id : Long) : List<SongArtistsEntity>
    @Query("SELECT a FROM songs_artists a JOIN FETCH a.song LEFT JOIN FETCH a.artist WHERE a.song.id = :song_id")
    fun findBySongId(song_id : Long) : List<SongArtistsEntity>

    @Query("SELECT a FROM songs_artists a JOIN FETCH a.song LEFT JOIN FETCH a.artist WHERE a.song.title like %:keyword%")
    fun findBySongTitleContaining(keyword: String) : Map<SongEntity,List<ArtistEntity>>
}
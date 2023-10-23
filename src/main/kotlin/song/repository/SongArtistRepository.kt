package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository

interface SongArtistRepository : JpaRepository<SongArtistEntity,Long>{
    fun findAllBySongTitleContains(keyword : String) : List<SongArtistEntity>
}
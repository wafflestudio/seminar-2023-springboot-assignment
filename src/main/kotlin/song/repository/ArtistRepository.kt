package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository

interface ArtistRepository : JpaRepository<ArtistEntity, Long> {
    fun findByName(name: String): ArtistEntity?
    fun findByNameIn(names: List<String>): List<ArtistEntity>
}

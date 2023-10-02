package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    fun findByTitleContaining(keyword: String): List<AlbumEntity>
}
package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SongRepository : JpaRepository<AlbumEntity, Long> {
    fun findByTitleContaining(keyword: String): List<SongEntity>
}
package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    @EntityGraph(attributePaths = ["album", "artists"])
    fun findByTitleContaining(keyword: String): List<SongEntity>

    fun findAllByIdIn(ids: List<Long>): List<SongEntity>
}

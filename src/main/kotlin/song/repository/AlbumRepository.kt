package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    @Query("""
        select a from albums a
        join fetch a.artist ar
        where a.title like %:keyword% order by length(a.title) asc
    """)
    fun findAllByTitleKeywordWithJoinFetch(keyword: String): List<AlbumEntity>
}
package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    @Query("""
select a
from albums a
left join fetch a.artist
left join fetch a.songs
where a.title like %:keyword%
order by length(a.title) 
    """)
    fun findAllByTitleContaining(keyword: String): List<AlbumEntity>
}
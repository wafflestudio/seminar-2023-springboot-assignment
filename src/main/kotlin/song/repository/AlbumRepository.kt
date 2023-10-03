package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    @Query("""
        select s 
        from albums s 
        join fetch s.artist
        join fetch s.songs
        where s.title like %:keyword%
        order by length(s.title)
    """)
    fun findAllAlbumsByTitle(keyword: String): Set<AlbumEntity>
}
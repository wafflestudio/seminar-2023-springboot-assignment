package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository: JpaRepository<SongEntity, Long> {
    @Query("""
select s
from songs s
left join fetch s.album
left join fetch s.artists sa left join fetch sa.artist
where s.title like %:keyword%
order by length(s.title) 
    """)
    fun findAllByTitleContains(keyword: String): List<SongEntity>
}
//left join fetch s.artists sa join fetch sa.artist
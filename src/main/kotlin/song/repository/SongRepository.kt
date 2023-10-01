package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository: JpaRepository<SongEntity, Long> {
    @Query("""
        select s from songs s
        join fetch s.album al
        join fetch s.artists sa
        join fetch sa.artist
        where s.id in :ids
    """)
    fun findByIdsWithJoinFetch(ids: List<Long>): List<SongEntity>
}
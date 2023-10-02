package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {
    @Query("""
        select p from playlists p
        left join fetch p.songs ps
        join fetch ps.song s
        where p.id = :id
    """)
    fun findByIdWithJoinFetch(id: Long): PlaylistEntity?
}
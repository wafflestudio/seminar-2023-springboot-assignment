package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {
    @Query("select p from playlists p join fetch p.playlistSongs ps where p.id=:id")
    override fun findById(id: Long): Optional<PlaylistEntity>

    fun findByIdEquals(id:Long) : PlaylistEntity?
}
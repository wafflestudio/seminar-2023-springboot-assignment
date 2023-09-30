package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

interface PlaylistRepository: JpaRepository<PlaylistEntity, Long> {
    @EntityGraph(attributePaths = ["group", "playlist_songs", "playlist_likes"])
    override fun findById(id: Long): Optional<PlaylistEntity>
}
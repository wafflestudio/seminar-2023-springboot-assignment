package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PlaylistRepository: JpaRepository<PlaylistEntity, Long> {
    fun findPlaylistEntityById(id: Long): PlaylistEntity?
}
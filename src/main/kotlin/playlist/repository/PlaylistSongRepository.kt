package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.Playlist
import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistSongRepository: JpaRepository<PlaylistSongEntity, Long> {
}
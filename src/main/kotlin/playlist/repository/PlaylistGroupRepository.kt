package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistGroup
import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistGroupRepository: JpaRepository<PlaylistGroupEntity, Long> {
    fun findAllByOpenTrueAndPlaylistsIsNotEmpty(): List<PlaylistGroupEntity>
}
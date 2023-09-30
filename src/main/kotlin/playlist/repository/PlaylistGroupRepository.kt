package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository : JpaRepository<PlaylistGroupEntity, Long> {

    @Query("SELECT pg FROM playlistGroups pg LEFT JOIN FETCH pg.playlists")
    fun findAllWithJoinFetch(): List<PlaylistGroupEntity>
}
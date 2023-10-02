package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository: JpaRepository<PlaylistGroupEntity, Long> {
    @Query("SELECT g FROM playlist_groups g LEFT JOIN FETCH g.playlists p WHERE SIZE(p) > 0 and g.open = TRUE")
    fun findWithJoinFetch() : List<PlaylistGroupEntity>
}
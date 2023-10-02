package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository: JpaRepository<PlaylistGroupEntity, Long> {
    @Query("""
select pg 
from playlist_groups pg
inner join fetch pg.playlists 
where pg.open=true 
    """)
    fun findAllByOpenTrueAndPlaylistsIsNotEmpty(): List<PlaylistGroupEntity>
}
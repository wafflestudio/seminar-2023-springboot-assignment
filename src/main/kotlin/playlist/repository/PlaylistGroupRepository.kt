package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository :JpaRepository<PlaylistGroupEntity,Long>{
    @Query("select pg from playlist_groups pg left join fetch pg.playlists where pg.open =:open ")
    fun findAllByOpenIs(open : Boolean):List<PlaylistGroupEntity>
}
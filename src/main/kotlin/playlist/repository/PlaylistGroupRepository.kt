package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

<<<<<<< HEAD
interface PlaylistGroupRepository :JpaRepository<PlaylistGroupEntity,Long>{
    @Query("SELECT p FROM playlist_groups p LEFT JOIN FETCH p.playlists WHERE p.open = :open")
    fun findByOpenWithJoinFetch(open:Boolean):List<PlaylistGroupEntity>
}
=======
interface PlaylistGroupRepository : JpaRepository<PlaylistGroupEntity, Long> {
    @Query("SELECT pg FROM playlist_groups pg JOIN FETCH pg.playlists WHERE pg.open = true")
    fun findAllOpenWithAnyPlaylists(): List<PlaylistGroupEntity>
}
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1

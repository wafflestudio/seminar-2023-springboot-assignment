package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistsRepository : JpaRepository<PlaylistsEntity, Long> {

    @Query("SELECT pg FROM playlist_groups AS pg LEFT JOIN FETCH pg.playlists")
    fun findAllOpenGroups(): List<PlaylistGroupsEntity>
    @Query("SELECT p FROM playlists AS p " +
            "LEFT JOIN FETCH p.playlist_group " +
            "LEFT JOIN FETCH p.playlist_songs AS ps " +
            "LEFT JOIN FETCH ps.song AS s " +
            "LEFT JOIN FETCH s.album AS a " +
            "LEFT JOIN FETCH a.artist " +
            "WHERE p.id = :id")
    fun findPlaylistsEntityById(id: Long): PlaylistsEntity?
}
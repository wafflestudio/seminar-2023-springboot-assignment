package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository: JpaRepository<PlaylistGroupEntity, Long> {
    // 모든 그룹인지 playlists를 가지고 있는 그룹만인지 모르겠어서 일단 left join 만약 playlists가 비어있는 그룹은 제외하고 싶다면 inner join
    @Query("SELECT pg FROM playlist_groups pg LEFT JOIN FETCH pg.playlists")
    fun findAllWithPlaylists(): List<PlaylistGroupEntity>
}
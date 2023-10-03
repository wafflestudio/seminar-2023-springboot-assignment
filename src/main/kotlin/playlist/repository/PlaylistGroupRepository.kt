package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository: JpaRepository<PlaylistGroupEntity, Long> {
    @Query("SELECT pg FROM playlist_groups pg " + "LEFT JOIN FETCH pg.playlists") //playlist 그룹들을 jpql 쿼리로 찾기
    fun findAllWithPlaylists(): List<PlaylistGroupEntity> //모든 플레이리스트 그룹을 찾으라는 함수(플레이리스트 정보도 같이 반환해줘야 하므로 join 해서 탐색하기)

}
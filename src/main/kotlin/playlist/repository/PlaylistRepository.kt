package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {
    @Query("SELECT pl FROM playlists pl " + "LEFT JOIN FETCH pl.playlistSongs ps" + " LEFT JOIN FETCH ps.song s " + "LEFT JOIN FETCH s.album a " + "LEFT JOIN FETCH a.artist" + " WHERE pl.id = :id")
    //jpql의 쿼리문을 이용해서 게속 잇따라 검색해서 들어가는 상황의 쿼리문을 작성해준다(sql 쿼리문하고는 형태가 살짝 다름, join fetch를 단위별로 분리 가능)
    fun findByIdWithSongs(id: Long): PlaylistEntity? //id로 플레이리스트 검색(플레이리스트 반환시에 song 정보도 같이 반환해줘야 하므로 join해서 검색하라)

}
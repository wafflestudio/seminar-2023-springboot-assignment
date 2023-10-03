package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    //SongEntity에서 songArtist를 찾기 -> songArtist에서 Artists를 찾는 전략 사용
    @Query("SELECT s FROM songs s " + "INNER JOIN FETCH s.songArtists sa " + "INNER JOIN FETCH sa.artist" + " INNER JOIN FETCH s.album  " + "WHERE s.id IN :songIds" )
    fun findSongEntitiesByIdsWithArtists(songIds: List<Long>): List<SongEntity> //id에 맞는 songentitiy를 찾아라(artist 정보 같이 반환해야 하므로 join)

    @Query("SELECT s FROM songs s " + "INNER JOIN FETCH s.songArtists sa " + "INNER JOIN FETCH sa.artist " + " INNER JOIN FETCH s.album  "+ " WHERE s.title LIKE %:keyword% ORDER BY LENGTH(s.title)")
    fun findByTitleContainingWithArtists(keyword: String): List<SongEntity> // 키워드를 포함하는 모든 노래를 제목 길이가 짧은 순으로 검색하라(artist 정보를 같이 반환해야 하므로 join 필수)

}
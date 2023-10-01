package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongRepository : JpaRepository<SongEntity, Long> {
    // Playlist DTO에서 list<Song>이 필요하고 Song에는 List<Artist>가 필요함 따라서 SongEntity에서 songArtist를 찾고 거기서 Artists를 찾을 것
    // 분명히 album도 inner join 했는데 query counter에서 실패
    @Query("SELECT s FROM songs s INNER JOIN FETCH s.songArtists sa INNER JOIN FETCH sa.artist " +
            "INNER JOIN FETCH s.album a " +
            " INNER JOIN FETCH a.artist WHERE s.id IN :songIds" +
            " ORDER BY LENGTH(s.title)")
    fun findSongEntitiesByIdsWithArtists(songIds: List<Long>): List<SongEntity>
    // 키워드를 포함하는 모든 노래 제목 검색후 아티스트를 미리 준비
    @Query("SELECT s FROM songs s INNER JOIN FETCH s.songArtists sa INNER JOIN FETCH sa.artist" +
            " WHERE s.title LIKE %:keyword% ORDER BY LENGTH(s.title)")
    fun findByTitleContainingWithArtists(keyword: String): List<SongEntity>
}

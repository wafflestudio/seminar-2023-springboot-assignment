package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
interface AlbumRepository : JpaRepository<AlbumEntity, Long>{
    @Query("SELECT a FROM albums a " + "INNER JOIN FETCH a.artist " + "WHERE a.title LIKE %:keyword%" + "ORDER BY LENGTH(a.title) ASC")
    fun findByTitleContainingWithArtistOrderByTitleAsc(keyword: String): List<AlbumEntity> // 키워드가 들어가면서, 검색결과는 제목이 짧은 순으로 정렬되도록 앨범을 찾아라(artist도 같이 반환해야 하므로 join 필수)

}
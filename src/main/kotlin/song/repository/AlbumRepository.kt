package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

<<<<<<< HEAD
interface AlbumRepository : JpaRepository<AlbumEntity, Long>{

    @Query("SELECT a FROM albums a LEFT JOIN FETCH a.artist WHERE a.title LIKE :title")
    fun findByTitleLike(title:String) : List<AlbumEntity>
}
=======
interface AlbumRepository : JpaRepository<AlbumEntity, Long> {
    @Query(
        """
        SELECT a FROM albums a
        JOIN FETCH a.artist
        WHERE a.title LIKE %:keyword%
        """
    )
    fun findAllByTitleContainingWithJoinFetch(keyword: String): List<AlbumEntity>

    fun findByTitle(title: String): AlbumEntity?
}
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1

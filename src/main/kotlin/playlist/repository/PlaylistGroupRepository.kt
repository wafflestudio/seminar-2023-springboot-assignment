package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistGroupRepository : JpaRepository<PlaylistGroupEntity, Long> {
//    @Query
//    fun findByIdWithJoinFetch(id: Long): PlaylistGroupEntity?
}
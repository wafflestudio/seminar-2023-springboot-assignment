package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistSongsRepository : JpaRepository<PlaylistSongsEntity, Long> {

}
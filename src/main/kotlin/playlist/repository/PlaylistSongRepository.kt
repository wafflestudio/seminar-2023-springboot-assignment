package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.JpaRepository
interface PlaylistSongRepository:JpaRepository<PlaylistSongEntity, Long> {
    //여기서는 따로 기능을 구현할 필요는 없을것 같은데?
}
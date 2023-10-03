package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SongArtistRepository : JpaRepository<SongArtistEntity, Long> {
    //여기서는 별다른 기능 구현이 필요 없을듯 한데
}
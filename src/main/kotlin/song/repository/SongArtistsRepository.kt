package com.wafflestudio.seminar.spring2023.song.repository

import org.springframework.data.jpa.repository.JpaRepository

interface SongArtistsRepository : JpaRepository<SongArtistsEntity, Long>
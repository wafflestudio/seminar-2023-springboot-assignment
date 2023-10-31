package com.wafflestudio.seminar.spring2023.customplaylist.repository

import org.springframework.data.jpa.repository.JpaRepository

interface CustomPlaylistRepository : JpaRepository<CustomPlaylistEntity, Long>

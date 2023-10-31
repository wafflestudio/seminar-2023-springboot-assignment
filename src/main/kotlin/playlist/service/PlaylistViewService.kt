package com.wafflestudio.seminar.spring2023.playlist.service

import java.time.LocalDateTime
import java.util.concurrent.Future

interface PlaylistViewService {
    fun create(playlistId: Long, userId: Long, at: LocalDateTime = LocalDateTime.now()): Future<Boolean>
}

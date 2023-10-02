package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl (
): PlaylistService {
    override fun getGroups(): List<PlaylistGroup> {
        TODO()
    }

    override fun get(id: Long): Playlist {
        TODO()
    }
}

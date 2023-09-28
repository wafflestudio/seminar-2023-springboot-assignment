package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.stereotype.Service

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        TODO("Not yet implemented")
    }

    override fun get(id: Long): Playlist {
        TODO("Not yet implemented")
    }
}

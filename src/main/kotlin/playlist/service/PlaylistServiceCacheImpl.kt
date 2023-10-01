package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.common.SimpleCache
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
@Primary
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {
    
    private val playlistGroupsCache: SimpleCache<String, List<PlaylistGroup>> = SimpleCache()
    
    private val playlistCache: SimpleCache<Long, Playlist> = SimpleCache()

    override fun getGroups(): List<PlaylistGroup> {
        return playlistGroupsCache.get("playlistGroups") {
            impl.getGroups()
        }
    }

    override fun get(id: Long): Playlist {
        return playlistCache.get(id) {
            impl.get(id)
        }
    }
    
}

package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.util.SimpleCache
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
@Primary
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {

    private val groupCache = SimpleCache<Unit, List<PlaylistGroup>>(10)
    private val playlistCache = SimpleCache<Long, Playlist>(10)

    override fun getGroups(): List<PlaylistGroup> {
        return groupCache.get(Unit) ?: run {
            val data = impl.getGroups()
            groupCache.put(Unit, data)
            data
        }
    }

    override fun get(id: Long): Playlist {
        return playlistCache.get(id) ?: run {
            val data = impl.get(id)
            playlistCache.put(id, data)
            data
        }
    }

}

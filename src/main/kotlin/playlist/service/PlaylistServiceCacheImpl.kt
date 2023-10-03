package com.wafflestudio.seminar.spring2023.playlist.service

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.stereotype.Service

@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
    cacheBuilder: Caffeine<Any, Any>,
) : PlaylistService {

    private val playlistGroupsCache = cacheBuilder.build<Unit, List<PlaylistGroup>>()

    private val playlistCache = cacheBuilder.build<Long, Playlist>()

    override fun getGroups(): List<PlaylistGroup> {
        val cached = playlistGroupsCache.getIfPresent(Unit)

        if (cached != null) {
            return cached
        }

        return impl.getGroups().also { playlistGroupsCache.put(Unit, it) }
    }

    override fun get(id: Long): Playlist {
        val cached = playlistCache.getIfPresent(id)

        if (cached != null) {
            return cached
        }

        return impl.get(id).also { playlistCache.put(id, it) }
    }
}

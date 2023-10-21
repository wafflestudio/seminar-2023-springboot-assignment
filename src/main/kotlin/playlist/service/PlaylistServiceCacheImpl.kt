package com.wafflestudio.seminar.spring2023.playlist.service

import com.github.benmanes.caffeine.cache.Caffeine
import com.wafflestudio.seminar.spring2023.playlist.service.SortPlaylist.Type
import org.springframework.stereotype.Service

@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
    cacheBuilder: Caffeine<Any, Any>,
) : PlaylistService {
    private val playlistGroupsCache = cacheBuilder.build<Type, List<PlaylistGroup>>()
    private val playlistCache = cacheBuilder.build<Long, Playlist>()

    override fun getGroups(sortType: Type): List<PlaylistGroup> {
        val cached = playlistGroupsCache.getIfPresent(sortType)

        if (cached != null) {
            return cached
        }

        return impl.getGroups(sortType).also { playlistGroupsCache.put(sortType, it) }
    }

    override fun get(id: Long): Playlist {
        val cached = playlistCache.getIfPresent(id)

        if (cached != null) {
            return cached
        }

        return impl.get(id).also { playlistCache.put(id, it) }
    }
}

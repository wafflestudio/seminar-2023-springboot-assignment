package com.wafflestudio.seminar.spring2023.playlist.service

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {

    private val playlistGroupsCache = Caffeine.newBuilder()
        .maximumSize(1)
        .expireAfterWrite(Duration.ofSeconds(10))
        .build<Unit, List<PlaylistGroup>>()

    private val playlistCache = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterWrite(Duration.ofSeconds(10))
        .build<Long, Playlist>()

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

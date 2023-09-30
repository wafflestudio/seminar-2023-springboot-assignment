package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.time.Instant

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
@Primary
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {
    private val cacheGroups = HashMap<String, CacheItem<List<PlaylistGroup>>>()
    private val cachePlaylists = HashMap<Long, CacheItem<Playlist>>()
    private val ttl: Long = 10

    override fun getGroups(): List<PlaylistGroup> {
        val cachedItem = cacheGroups["groups"]
        if (cachedItem?.isExpired() == false) {
            return cachedItem.value
        }
        val groups = impl.getGroups()
        cacheGroups["groups"] = CacheItem(groups, ttl)
        return groups
    }

    override fun get(id: Long): Playlist {
        val cachedItem = cachePlaylists[id]
        if (cachedItem?.isExpired() == false) {
            return cachedItem.value
        }
        val playlist = impl.get(id)
        cachePlaylists[id] = CacheItem(playlist, ttl)
        return playlist
    }

    private data class CacheItem<T>(val value: T, val ttl: Long) {
        private val creationTime = Instant.now()

        fun isExpired(): Boolean {
            return Instant.now().isAfter(creationTime.plusSeconds(ttl))
        }
    }
}

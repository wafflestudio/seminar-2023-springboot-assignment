package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.stereotype.Service

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {
    var groupCache = CacheData(listOf<PlaylistGroup>(), 0L)
    val playlistCacheMap = mutableMapOf<Long, CacheData<Playlist>>()

    override fun getGroups(): List<PlaylistGroup> {
        val currentTime = System.currentTimeMillis()
        if (groupCache.createdAt + 10000 < currentTime) {
            val groups = impl.getGroups()
            groupCache = CacheData(groups, currentTime)
        }
        return groupCache.data
    }

    override fun get(id: Long): Playlist {
        val currentTime = System.currentTimeMillis()
        if (playlistCacheMap[id]?.createdAt?.plus(10000) ?: 0 < currentTime) {
            val playlist = impl.get(id)
            playlistCacheMap[id] = CacheData(playlist, currentTime)
        }
        return playlistCacheMap[id]?.data ?: throw PlaylistNotFoundException()
    }
}

data class CacheData<T>(
    val data: T,
    val createdAt: Long,
)

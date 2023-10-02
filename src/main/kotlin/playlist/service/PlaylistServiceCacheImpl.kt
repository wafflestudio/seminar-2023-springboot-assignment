package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.stereotype.Service
import jakarta.persistence.Cacheable
import org.springframework.cache.annotation.CacheConfig

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {

    val playlistGroupCache = CustomCache<Long,List<PlaylistGroup>>()
    val playlistCache = CustomCache<Long,Playlist>()
    override fun getGroups(): List<PlaylistGroup> {
        //TODO("Not yet implemented")
        val playlistGroupsCached = playlistGroupCache.get(0L)
        if(playlistGroupsCached != null)
            return playlistGroupsCached
        val playlistGroups = impl.getGroups()
        playlistGroupCache.put(0L,playlistGroups)
        return playlistGroups
    }

    override fun get(id: Long): Playlist {
        //TODO("Not yet implemented")
        val playlistCached = playlistCache.get(id)
        if(playlistCached != null)
            return playlistCached
        val playlist = impl.get(id)
        playlistCache.put(playlist.id,playlist)
        return playlist
    }
}

package com.wafflestudio.seminar.spring2023.playlist.service

import com.github.benmanes.caffeine.cache.Caffeine
import com.wafflestudio.seminar.spring2023.playlist.service.SortPlaylist.Type
import org.springframework.stereotype.Service
import jakarta.persistence.Cacheable
import org.springframework.cache.annotation.CacheConfig

@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
    cacheBuilder: Caffeine<Any, Any>,
) : PlaylistService {
    private val playlistGroupsCache = cacheBuilder.build<Type, List<PlaylistGroup>>()
    private val playlistCache = cacheBuilder.build<Long, Playlist>()

<<<<<<< HEAD
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
=======
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
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1
    }
}

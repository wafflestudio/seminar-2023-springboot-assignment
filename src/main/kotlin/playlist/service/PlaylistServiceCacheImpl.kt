package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {
    private val playlistGroupCache: MutableMap<Long, PlaylistGroupCache> = ConcurrentHashMap()
    private val playlistCache: MutableMap<Long, PlaylistCache> = ConcurrentHashMap()
    override fun getGroups(): List<PlaylistGroup> {
        val currentTime = System.currentTimeMillis()
        val cachePlaylistGroup = playlistGroupCache[0]

        if (cachePlaylistGroup != null && (currentTime - cachePlaylistGroup.time <= TimeUnit.SECONDS.toMillis(10))) {
            return cachePlaylistGroup.playlistGroup
        }

        val group = impl.getGroups()
        playlistGroupCache[0] = PlaylistGroupCache(group, currentTime)
        return group
    }

    override fun get(id: Long): Playlist {
        val currentTime = System.currentTimeMillis()
        val cachedPlaylist = playlistCache[id]

        if (cachedPlaylist != null && (currentTime - cachedPlaylist.time <= TimeUnit.SECONDS.toMillis(10))) {
            return cachedPlaylist.playlist
        }

        val playlist = impl.get(id)
        playlistCache[id] = PlaylistCache(playlist, currentTime)
        return playlist
    }
    data class PlaylistCache(val playlist: Playlist, val time: Long)
    data class PlaylistGroupCache(val playlistGroup: List<PlaylistGroup>, val time: Long)
}

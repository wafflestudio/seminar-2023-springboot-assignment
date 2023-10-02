package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
//@Primary
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {

    private val groupsCache = ConcurrentHashMap<Boolean, CachedGroups>()
    private val playlistCache = ConcurrentHashMap<Long, CachedPlaylist>()

    override fun getGroups(): List<PlaylistGroup> {
        val cachedGroups = groupsCache[true]

        return if (cachedGroups != null && isCacheValid(cachedGroups)) {
            cachedGroups.groups
        } else {
            val groups = impl.getGroups()
            addToCache(groups)
            groups
        }
    }

    override fun get(id: Long): Playlist {
        val cachedPlaylist = playlistCache[id]

        return if (cachedPlaylist != null && isCacheValid(cachedPlaylist)) {
            cachedPlaylist.playlist
        } else {
            val playlist = impl.get(id)
            addToCache(id, playlist)
            playlist
        }
    }

    private fun isCacheValid(cachedPlaylist: CachedPlaylist): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        return currentTimeMillis - cachedPlaylist.timestamp < TimeUnit.SECONDS.toMillis(10)
    }

    private fun addToCache(id: Long, playlist: Playlist) {
        val currentTimeMillis = System.currentTimeMillis()
        playlistCache[id] = CachedPlaylist(playlist, currentTimeMillis)
    }

    private fun isCacheValid(cachedGroups: CachedGroups): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        return currentTimeMillis - cachedGroups.timestamp < TimeUnit.SECONDS.toMillis(10)
    }

    private fun addToCache(groups: List<PlaylistGroup>) {
        val currentTimeMillis = System.currentTimeMillis()
        groupsCache[true] = CachedGroups(groups, currentTimeMillis)
    }

    private data class CachedPlaylist(val playlist: Playlist, val timestamp: Long)
    private data class CachedGroups(val groups: List<PlaylistGroup>, val timestamp: Long)
}

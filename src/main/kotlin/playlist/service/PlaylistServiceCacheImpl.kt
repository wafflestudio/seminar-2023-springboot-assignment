package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.concurrent.ConcurrentHashMap

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {

    val ttl = 10_000L
    val groupKey = 0L
    var groupCacheMap = ConcurrentHashMap<Long, GroupCache>()
    var playlistCacheMap = ConcurrentHashMap<Long, PlaylistCache>()

    override fun getGroups(): List<PlaylistGroup> {
        val now = System.currentTimeMillis()

        if (groupCacheMap.containsKey(groupKey)) {
            val expireTime = groupCacheMap[groupKey]?.expireTime?:throw RuntimeException()
            val cachedGroups = groupCacheMap[groupKey]?.groups?:throw RuntimeException()

            return if (expireTime < now) {
                val groups = impl.getGroups()
                val groupCache = GroupCache(
                    expireTime = now + ttl,
                    groups = groups
                )
                groupCacheMap[groupKey] = groupCache
                groups
            } else {
                cachedGroups
            }

        } else {
            val groups = impl.getGroups()
            val groupCache = GroupCache(
                expireTime = now + ttl,
                groups = groups
            )
            groupCacheMap[groupKey] = groupCache
            return groups
        }
    }

    override fun get(id: Long): Playlist {
        val now = System.currentTimeMillis()

        if (playlistCacheMap.containsKey(id)) {
            val expireTime = playlistCacheMap[id]?.expireTime?:throw RuntimeException()
            val cachedPlaylist = playlistCacheMap[id]?.playlist?:throw RuntimeException()
            // 만료 되었다면
            return if (expireTime < now) {
                val playlist = impl.get(id)
                val playlistCache = PlaylistCache(
                    expireTime = now + ttl,
                    playlist = impl.get(id)
                )
                playlistCacheMap[id] = playlistCache
                playlist
            } else {
                cachedPlaylist
            }

        } else {
            val playlist = impl.get(id)
            val playlistCache = PlaylistCache(
                expireTime = now + ttl,
                playlist = playlist
            )
            playlistCacheMap[id] = playlistCache
            return playlist
        }
    }
}

data class GroupCache(val expireTime:Long, val groups:List<PlaylistGroup>)

data class PlaylistCache(val expireTime:Long, val playlist:Playlist)

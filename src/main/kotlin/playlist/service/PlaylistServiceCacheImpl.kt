package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.util.CacheManager
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.concurrent.ConcurrentHashMap

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {

    private final val TTL = 10L
    private final val groupKey = 0L
    private val gcm = CacheManager<Long, List<PlaylistGroup>>(TTL)
    private val pcm = CacheManager<Long, Playlist>(TTL)

    override fun getGroups(): List<PlaylistGroup> {
        return if (gcm.isCacheMiss(groupKey)) {
            val groups = impl.getGroups()
            gcm.put(groupKey, groups)
            groups
        } else {
            gcm.get(groupKey)
        }
    }

    override fun get(id: Long): Playlist {
        return if (pcm.isCacheMiss(id)) {
            val playlist = impl.get(id)
            pcm.put(id, playlist)
            playlist
        } else {
            pcm.get(id)
        }
    }
}
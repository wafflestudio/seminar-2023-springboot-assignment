package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.common.util.CacheUtils
import org.springframework.stereotype.Service

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {
    private val PLAYLIST_GROUP_KEY = "Playlist Group Key"
    private val CACHE_TIME: Long = 10000L
    private val playlistGroupListCache = CacheUtils<String, List<PlaylistGroup>>(CACHE_TIME)
    private val playlistCache = CacheUtils<Long, Playlist>(CACHE_TIME)
    override fun getGroups(): List<PlaylistGroup> {
        var groups = playlistGroupListCache.get(PLAYLIST_GROUP_KEY)
        if (groups == null) {
            groups = impl.getGroups()
            playlistGroupListCache.put(PLAYLIST_GROUP_KEY, groups)
        }
        return groups
    }

    override fun get(id: Long): Playlist {
        var playlist = playlistCache.get(id)
        if (playlist == null) {
            playlist = impl.get(id)
            playlistCache.put(id, playlist)
        }
        return playlist
    }
}

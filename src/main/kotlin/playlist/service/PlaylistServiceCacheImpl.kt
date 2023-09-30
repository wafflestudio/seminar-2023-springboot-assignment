package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.CacheTTLImpl
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
@Qualifier("playlistServiceCacheImpl")
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
    private val cacheTTLImpl: CacheTTLImpl
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        val cachedGroups = cacheTTLImpl.getFromCache(Unit)
        return if (cachedGroups == null) {
            val groups = impl.getGroups()
            cacheTTLImpl.addToCache(Unit, groups)
            groups
        } else {
            cachedGroups as List<PlaylistGroup>
        }
    }

    override fun get(id: Long): Playlist {
        val cachedPlaylist = cacheTTLImpl.getFromCache(id)
        return if (cachedPlaylist == null) {
            val playlist = impl.get(id)
            cacheTTLImpl.addToCache(id, playlist)
            playlist
        } else {
            cachedPlaylist as Playlist
        }
    }
}

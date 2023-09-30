package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.util.Cache
import org.springframework.stereotype.Service

@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {
    private val groupCache = Cache<Unit, List<PlaylistGroup>>(10)
    private val playlistCache = Cache<Long, Playlist>(10)

    override fun getGroups(): List<PlaylistGroup> {
        if (groupCache[Unit]==null) {
            val groups = impl.getGroups()
            groupCache[Unit] = groups
        }
       return requireNotNull(groupCache[Unit])
    }

    override fun get(id: Long): Playlist {
        if (playlistCache[id]==null) {
            val playlist = impl.get(id)
            playlistCache[id] = playlist
        }
        return requireNotNull(playlistCache[id])
    }
}

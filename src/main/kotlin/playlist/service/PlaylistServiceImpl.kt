package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.common.util.SongUtils
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import jakarta.transaction.Transactional
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val playlistRepository: PlaylistRepository
    ) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        val playlistGroups = playlistGroupRepository.findOpenPlaylistGroups()
        return playlistGroups.filter { it.playlists.isNotEmpty() }.map { PlaylistGroup(
            id = it.id,
            title = it.title,
            playlists = it.playlists.map { playlistEntity -> PlaylistBrief(
                id = playlistEntity.id,
                title = playlistEntity.title,
                subtitle = playlistEntity.subtitle,
                image = playlistEntity.image,
            )}
        )}
    }

    override fun get(id: Long): Playlist {
        val playlist = playlistRepository.findPlaylistEntityById(id) ?: throw PlaylistNotFoundException()
        val songList = playlist.songs.map { SongUtils.mapSongEntityToSong(it) }
        return Playlist(
            id = playlist.id,
            title = playlist.title,
            subtitle = playlist.subtitle,
            image = playlist.image,
            songs = songList.sortedBy { it.id }
        )
    }
}

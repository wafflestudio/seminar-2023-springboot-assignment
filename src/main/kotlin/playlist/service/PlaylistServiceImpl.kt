package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val playlistRepository: PlaylistRepository,
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> =
        playlistGroupRepository.findAllOpenGroups()
            .filter { it.playlists.isNotEmpty() }
            .map { it.toPlaylistGroup() }

    override fun get(id: Long): Playlist =
        playlistRepository.findByIdOrNull(id)?.toPlaylist()
            ?: throw PlaylistNotFoundException()
}

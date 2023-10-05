package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl (
        val playlistGroupRepository: PlaylistGroupRepository,
        val playlistRepository: PlaylistRepository
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        val playlistGroupsEntities = playlistGroupRepository.findAllByOpenTrueAndPlaylistsIsNotEmpty()
        return playlistGroupsEntities.map(::PlaylistGroup)
        }

    override fun get(id: Long): Playlist {
        val playlistEntity = playlistRepository.findPlaylistEntityById(id) ?: throw PlaylistNotFoundException()
        return Playlist(playlistEntity)
    }
}
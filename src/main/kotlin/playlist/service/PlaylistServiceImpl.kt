package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl (
        val playlistRepository: PlaylistRepository,
        val playlistGroupRepository: PlaylistGroupRepository
        ) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        val playlistGroupEntityList = playlistGroupRepository.findNonEmptyGroupsByOpen(true)
        return playlistGroupEntityList.map {
            playlistGroupEntity -> PlaylistGroup(playlistGroupEntity)
        }
    }

    override fun get(id: Long): Playlist {
        val playlistEntity = playlistRepository.findByIdWithSongs(id) ?: throw PlaylistNotFoundException()
        return Playlist(playlistEntity)
    }
}

package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.*
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    val playlistGroupRepository: PlaylistGroupRepository,
    val playlistRepository: PlaylistRepository,
    val songRepository: SongRepository,
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        return playlistGroupRepository.findNotEmptyGroupsByOpen(true).map {
            PlaylistGroup(it)
        }
    }

    override fun get(id: Long): Playlist {
        val playlistEntity = playlistRepository.findByIdWithJoinFetch(id) ?: throw PlaylistNotFoundException()
        val songEntities = songRepository.findByIdsWithJoinFetch(playlistEntity.songs.map { it.song.id })
        return Playlist(playlistEntity, songEntities)
    }
}
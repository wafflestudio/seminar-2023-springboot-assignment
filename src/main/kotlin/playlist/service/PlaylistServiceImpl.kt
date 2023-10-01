package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository,
    private val playlistGroupRepository: PlaylistGroupRepository,
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        return playlistGroupRepository.findAllWithJoinFetch()
            .map { PlaylistGroup(it) }
    }

    override fun get(id: Long): Playlist {
        val playlist = playlistRepository.findByIdWithJoinFetch(id) ?: throw PlaylistNotFoundException()
        val songs = songRepository.findBySongsWithJoinFetch(playlist.songs.map { it.song.id })
        return Playlist(playlist, songs)
    }

}

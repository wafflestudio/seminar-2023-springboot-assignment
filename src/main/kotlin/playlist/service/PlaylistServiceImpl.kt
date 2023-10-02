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
        return playlistGroupRepository.findAll();
    }

    override fun get(id: Long): Playlist {
        return playlistRepository.findByIdWithSongs(id);
    }
}

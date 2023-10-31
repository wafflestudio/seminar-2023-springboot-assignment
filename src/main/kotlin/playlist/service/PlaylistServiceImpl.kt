package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository,
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        //TODO()
        val playlistGroupEntities = playlistGroupRepository.findByOpenWithJoinFetch(true)
        val playlistGroups =  playlistGroupEntities.map { it.toPlaylistGroup() }
        val nonEmptyPlaylistGroups: MutableList<PlaylistGroup> = mutableListOf()
        for(playlistGroup in playlistGroups) {
            if (playlistGroup.playlists.isNotEmpty())
                nonEmptyPlaylistGroups += playlistGroup
        }
        return nonEmptyPlaylistGroups
    }

    override fun get(id: Long): Playlist {
        //TODO()
        val playlistEntity = playlistRepository.findByIdWithJoinFetch(id) ?: throw PlaylistNotFoundException()
        val songEntities =
            songRepository.findByIdInJoinFetch(playlistEntity.playlistSongs.map { it.song }.map { it.id })
        return playlistEntity.toPlaylist()
    }
}

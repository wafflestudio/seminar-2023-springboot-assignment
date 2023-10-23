package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.*
import com.wafflestudio.seminar.spring2023.song.repository.Song
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val songRepository: SongRepository,
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        val openPlaylistGroup: List<PlaylistGroupEntity> = playlistGroupRepository.findAllByOpenIs(true)
        return openPlaylistGroup.filter { it.playlists.isNotEmpty() }.map { PlaylistGroup(it) }
    }

    override fun get(id: Long): Playlist {
        val playlistEntity = playlistRepository.findById(id)
        if(playlistEntity.isEmpty){
            throw PlaylistNotFoundException()
        }
        val playlistSongList = playlistEntity.get().playlistSongs

        val songEntityList = songRepository.findSongEntitiesByIdIn(playlistSongList.map { it.song.id })

        return Playlist(playlistEntity, songEntityList)
    }
}

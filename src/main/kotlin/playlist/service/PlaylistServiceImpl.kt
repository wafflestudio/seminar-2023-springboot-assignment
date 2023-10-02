package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongsEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
//import com.wafflestudio.seminar.spring2023.song.service.SongEntityMapper
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
class PlaylistServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository,
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val playlistSongsEntity: PlaylistSongsEntity,
) : PlaylistService {
    override fun getGroups(): List<PlaylistGroup> {
        return playlistGroupRepository.findAllWithFetchJoin()
            .map { PlaylistGroup(it) }
        //val playlistGroupEntities = playlistGroupRepository.findAllWithFetchJoin().filter { it.playlists.isNotEmpty() }

        //return playlistGroupEntities.map { entity ->
        //    PlaylistMapper.toPlayListGroup(entity)
        }


    override fun get(id: Long): Playlist {
        val playlist = playlistRepository.findByIdWithSongs(id) ?: throw PlaylistNotFoundException()
        val songs = songRepository.searchSongIWithFetchJoin(playlist.playlistSongs.map { it.song.id })
        return Playlist(playlist, songs)

    }
}

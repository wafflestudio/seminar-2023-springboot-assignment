package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
class PlaylistServiceImpl(
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository
) : PlaylistService {
    override fun getGroups(): List<PlaylistGroup> {
        val playlistGroupEntities = playlistGroupRepository.findByOpenTrue().filter { it.playlists.isNotEmpty() }

        return playlistGroupEntities.map { entity ->
            PlaylistMapper.toPlayListGroupDTO(entity)
        }
    }

    override fun get(id: Long): Playlist {
        val playlistEntity = playlistRepository.findByIdWithSongs(id) ?: throw PlaylistNotFoundException()
        val songsEntity = fetchSongsWithArtists(playlistEntity)

        return PlaylistMapper.toPlaylistDTO(playlistEntity, songsEntity)
    }

    private fun fetchSongsWithArtists(playlistEntity: PlaylistEntity): List<SongEntity> {
        val songIds = playlistEntity.playlistSongs.map { it.song.id }
        return songRepository.findByIdsWithJoinFetch(songIds)
    }
}

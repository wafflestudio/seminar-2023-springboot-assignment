package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository,
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> =
        playlistGroupRepository.findAllOpenWithAnyPlaylists().map(::PlaylistGroup)

    override fun get(id: Long): Playlist {
        val playlist = playlistRepository.findByIdWithSongs(id) ?: throw PlaylistNotFoundException()

        val songs = songRepository.findAllByIdWithJoinFetch(ids = playlist.songs.map { it.song.id })

        return Playlist(playlist, songs)
    }
}

private fun PlaylistGroup(entity: PlaylistGroupEntity) = PlaylistGroup(
    id = entity.id,
    title = entity.title,
    playlists = entity.playlists.map(::PlaylistBrief)
)

private fun PlaylistBrief(entity: PlaylistEntity) = PlaylistBrief(
    id = entity.id,
    title = entity.title,
    subtitle = entity.subtitle,
    image = entity.image
)

private fun Playlist(entity: PlaylistEntity, songEntities: List<SongEntity>) = Playlist(
    id = entity.id,
    title = entity.title,
    subtitle = entity.subtitle,
    image = entity.image,
    songs = songEntities.map(::Song)
)

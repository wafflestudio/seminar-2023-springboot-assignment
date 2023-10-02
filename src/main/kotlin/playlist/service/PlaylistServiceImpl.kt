package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.toSong
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository
) : PlaylistService  {

    override fun getGroups(): List<PlaylistGroup> {
        val playlistGroups = playlistGroupRepository.findByOpenWithJoinFetch()
        return playlistGroups.map { playlistGroup ->
            PlaylistGroup(
                id = playlistGroup.id,
                title = playlistGroup.title,
                playlists = playlistGroup.playlists.map {
                    it.toPlaylistBrief()
                }.toList(),
            )
        }
    }

    override fun get(id: Long): Playlist {
        val playlist = playlistRepository.findByIdWithJoinFetch(id)
            ?: throw PlaylistNotFoundException()
        return Playlist(
            id = playlist.id,
            title = playlist.title,
            subtitle = playlist.subtitle,
            image = playlist.image,
            songs = songRepository
                .findByIdInWithJoinFetch(playlist.playlistSongs.map { it.song.id })
                .map { it.toSong() }
                .toList(),
        )
    }
}

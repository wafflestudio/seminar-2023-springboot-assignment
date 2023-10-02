package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val playlistGroupRepository: PlaylistGroupRepository,
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> = playlistGroupRepository
        .findAllByOpenTrueAndPlaylistsIsNotEmpty()
        .map { PlaylistGroup(it) }

    override fun get(id: Long): Playlist {
        val playlist = playlistRepository.findPlaylistEntityById(id)?: throw PlaylistNotFoundException()
        return Playlist(
            id = playlist.id,
            title = playlist.title,
            subtitle = playlist.subtitle,
            image = playlist.image,
            songs = playlist.songs.map { Song(it.song) }
        )
    }
}

fun PlaylistGroup(entity: PlaylistGroupEntity): PlaylistGroup = PlaylistGroup(entity.id, entity.title, entity.playlists.map { PlaylistBrief(it) })

fun PlaylistBrief(entity: PlaylistEntity): PlaylistBrief = PlaylistBrief(entity.id, entity.title, entity.subtitle, entity.image)
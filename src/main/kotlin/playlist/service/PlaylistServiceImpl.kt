package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
        private val playlistGroupRepository: PlaylistGroupRepository,
        private val playlistRepository: PlaylistRepository,
        private val playlistSongRepository: PlaylistSongRepository
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        //TODO()
        val playlistGroupList = playlistGroupRepository.findPlaylistGroupEntityByOpen(true).filter { it.playlist.isNotEmpty() }
        return playlistGroupList.map { PlaylistGroup(
                id = it.id,
                title = it.title,
                playlists = it.playlist.map { PlaylistBrief(it.id, it.title, it.subtitle, it.image) }
        ) }
    }

    override fun get(id: Long): Playlist {
        //TODO()
        val playlist = playlistRepository.findPlaylistEntityById(id) ?: throw PlaylistNotFoundException()
        val songId = playlist.playlistSong.map { it.song.id }
        val playlistSong = playlistSongRepository.findSongsInPlaylist(songId)
        return Playlist(
                id = playlist.id,
                title = playlist.title,
                subtitle = playlist.subtitle,
                image = playlist.image,
                songs = playlistSong.map { songEntityToSong(it) }.sortedBy { it.id }
        )
    }

    fun songEntityToSong(songEntity: SongEntity): Song {
        return Song(
                id = songEntity.id,
                title = songEntity.title,
                artists = songEntity.songArtist.map { Artist(it.artist.id, it.artist.name) },
                album = songEntity.album.title,
                image = songEntity.album.image,
                duration = songEntity.duration.toString()
        )
    }
}

package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val playlistRepository: PlaylistRepository,
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        val groupEntity = playlistGroupRepository.findOpenGroups()
            .map { entity -> PlaylistGroup(entity.id, entity.title, entity.playlists
                .map{
                        playlist -> PlaylistBrief(playlist.id, playlist.title, playlist.subtitle, playlist.image)
                })
            }
        return groupEntity
    }

    override fun get(id: Long): Playlist {
        val entity = playlistRepository.findById(id).orElseThrow {PlaylistNotFoundException()}
        val playlist_song = playlistRepository.findSongs(id)
        return Playlist(entity.id, entity.title, entity.subtitle, entity.image, playlist_song.map{
            it -> Song(
            it.songOfPlaylist.id,
            it.songOfPlaylist.title,
            it.songOfPlaylist.song_artists.map {
                it -> Artist(it.artistOfSong.id, it.artistOfSong.name)
            },
            it.songOfPlaylist.album.title,
            it.songOfPlaylist.album.image,
            it.songOfPlaylist.duration.toString())
        })
    }
}

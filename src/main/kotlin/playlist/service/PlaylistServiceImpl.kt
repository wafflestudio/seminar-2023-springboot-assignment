package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongRepository
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
        val playlistGroupOpened = playlistGroupRepository.findByOpenTrueWithJoinFetch()
        val results = playlistGroupOpened.filter { it.playlists.isNotEmpty() }

        return results.map { playlistGroupEntity ->
            PlaylistGroup(
                id = playlistGroupEntity.id,
                title = playlistGroupEntity.title,
                playlists = playlistGroupEntity.playlists.map {playlistEntity ->
                    PlaylistBrief(
                        id = playlistEntity.id,
                        title = playlistEntity.title,
                        subtitle = playlistEntity.subtitle,
                        image = playlistEntity.image
                    )
                }
            )
        }
    }

    override fun get(id: Long): Playlist {
        val playlistFound = playlistRepository.findByIdWithJoinFetch(id) ?: throw PlaylistNotFoundException()
        val songs = playlistFound.playlist_songs.map {playlistSongEntity ->
            Song(
                id = playlistSongEntity.song.id,
                title = playlistSongEntity.song.title,
                album = playlistSongEntity.song.album.title,
                image = playlistSongEntity.song.album.image,
                duration = playlistSongEntity.song.duration,
                artists = playlistSongEntity.song.song_artists.map {songArtistEntity ->
                    Artist(
                        id = songArtistEntity.artist.id,
                        name = songArtistEntity.artist.name,
                    )
                }
            )
        }.sortedBy {it.id}

        return Playlist(
            id = playlistFound.id,
            title = playlistFound.title,
            subtitle = playlistFound.subtitle,
            image = playlistFound.image,
            songs = songs
        )
    }
}

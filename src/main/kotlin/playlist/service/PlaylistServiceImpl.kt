package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        TODO()
    }

    override fun get(id: Long): Playlist {
        val playlistEntity = playlistRepository.findById(id).orElseThrow { PlaylistNotFoundException() }
        val playlistSongsEntities = playlistEntity.playlist_songs
        val songEntities = playlistSongsEntities.map { it.song }

        val songs = songEntities.map {
            Song(
                id = it.id,
                title = it.title,
                album = it.album.title,
                image = it.album.image,
                duration = it.duration.toString(),
                artists = it.songArtists.map {
                        it2 -> Artist(
                    id = it2.artist.id,
                    name = it2.artist.name
                )
                }
            )
        }

        return Playlist(
            id = playlistEntity.id,
            title = playlistEntity.title,
            subtitle = playlistEntity.subtitle,
            image = playlistEntity.image,
            songs = songs
        )
    }
}

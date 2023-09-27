package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

//@Primary
@Service
class PlaylistServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val songRepository: SongRepository,
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        val groups = playlistGroupRepository.findAllWithJoinFetch()
                .filter { it.open }
                .filter { it.playlists.isNotEmpty() }

        return groups.map { groupEntity ->
            PlaylistGroup(
                id = groupEntity.id,
                title = groupEntity.title,
                playlists = groupEntity.playlists.map { playlistEntity ->
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
        val playlistEntity = playlistRepository.findPlaylistWithSongsById(id)
                .orElseThrow { throw PlaylistNotFoundException() }

        val songIds = playlistEntity.playlist_songs.map { it.song.id }

        val songEntityList = songRepository.findSongsWithArtistsAndAlbumByIds(songIds)

        val songs = playlistEntity.playlist_songs.map { playlistSong ->
            val songEntity = playlistSong.song
            Song(
                id = songEntity.id,
                title = songEntity.title,
                artists = songEntityList.filter { it.id == songEntity.id }
                    .flatMap { it.song_artists }
                    .map { songArtistEntity ->
                        Artist(
                            id = songArtistEntity.artist.id,
                            name = songArtistEntity.artist.name
                        )
                    },
                album = songEntity.album.title,
                image = songEntity.album.image,
                duration = songEntity.duration.toString(),
            )
        }.sortedBy { it.id }

        // 최종적으로 Playlist DTO를 반환한다.
        return Playlist(
                id = playlistEntity.id,
                title = playlistEntity.title,
                subtitle = playlistEntity.subtitle,
                image = playlistEntity.image,
                songs = songs,
        )

    }
}



package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val songRepository: SongRepository
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        val groups = playlistGroupRepository.findWithJoinFetch()
        return groups.map {
            PlaylistGroup(
                id = it.id,
                title = it.title,
                playlists = it.playlists.map { playlist ->
                    PlaylistBrief(
                        id = playlist.id,
                        title = playlist.title,
                        subtitle = playlist.subtitle,
                        image = playlist.image
                    )
                }
            )
        }
    }

    override fun get(id: Long): Playlist {
        val playlist = playlistRepository.findByIdWithJoinFetch(id)
            ?: throw PlaylistNotFoundException()
        val songEntities = songRepository.findBySongIdWithJoinFetch(
                playlist.playlistSongs.map { it.song.id }
                )
        val songs = songEntities.map { songEntity ->
            Song(
                id = songEntity.id,
                title = songEntity.title,
                album = songEntity.album.title,
                image = songEntity.album.image,
                duration = songEntity.duration.toString(),
                artists = songEntity.songArtists.map {
                    Artist(
                        id = it.artist.id,
                        name = it.artist.name
                    )
                }
            )
        }

        return Playlist(
            id = playlist.id,
            title = playlist.title,
            subtitle = playlist.subtitle,
            image = playlist.image,
            songs = songs,
        )
    }
}

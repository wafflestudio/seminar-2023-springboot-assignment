package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupsEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistsEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistsRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.toSong
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
        private val playlistsRepository: PlaylistsRepository,
        private val songRepository: SongRepository,
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        val allOpenGroups = playlistsRepository.findAllOpenGroups()

        return allOpenGroups.filter { playlistGroupsEntity ->
             playlistGroupsEntity.open and playlistGroupsEntity.playlists.isNotEmpty()
        }.map { nonEmptyPlaylistGroupsEntity ->
            toPlaylistGroup(nonEmptyPlaylistGroupsEntity)
        }
    }

    override fun get(id: Long): Playlist {
        val playlistsEntity = playlistsRepository.findPlaylistsEntityById(id) ?: throw PlaylistNotFoundException()
        val songEntities = songRepository.findAllSongsInPlaylist(idList = playlistsEntity.playlist_songs.map {
            playlistSongsEntity -> playlistSongsEntity.song.id
        })

        return toPlaylist(playlistsEntity, songEntities)
    }
}

fun toPlaylistGroup(entity: PlaylistGroupsEntity) = PlaylistGroup(
        id = entity.id,
        title = entity.title,
        playlists = entity.playlists.map { playlistsEntity ->
            toPlaylistBrief(playlistsEntity)
        }
)

fun toPlaylistBrief(entity: PlaylistsEntity) = PlaylistBrief(
        id = entity.id,
        title = entity.title,
        subtitle = entity.subtitle,
        image = entity.image
)

fun toPlaylist(playlistEntity: PlaylistsEntity, songEntities: List<SongEntity>) = Playlist(
        id = playlistEntity.id,
        title = playlistEntity.title,
        subtitle = playlistEntity.subtitle,
        image = playlistEntity.image,
        songs = songEntities.map { songEntity ->
            toSong(songEntity)
        }
)

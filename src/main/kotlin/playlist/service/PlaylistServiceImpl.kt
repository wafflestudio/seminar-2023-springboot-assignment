package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.util.Optional

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val songRepository: SongRepository
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        val playlistGroupList = playlistGroupRepository.findAllByOpen(true)
            .filter { it.playlists.isNotEmpty() }
            .map {
                    playlistGroupEntity -> PlaylistGroup(
                id = playlistGroupEntity.id,
                title = playlistGroupEntity.title,
                playlists = playlistGroupEntity.playlists.map {
                        playlistEntity -> PlaylistBrief(playlistEntity.id, playlistEntity.title, playlistEntity.subtitle, playlistEntity.image)
                })}
        if (playlistGroupList.isNotEmpty()){
            return playlistGroupList
        } else throw PlaylistNotFoundException()
    }

    override fun get(id: Long): Playlist {
        val playlistEntity = playlistRepository.findByIDJoinFetch(id = id)
        if (playlistEntity != null) {
            val songIDList = playlistEntity.playlistSongs.map { it.song.id }
            val songEntityList = songRepository.findAllByIDListJoinFetch(songIDList)
            val songList : List<Song> =
                songEntityList
                    .filter { it.song_artists.isNotEmpty() }
                    .map {
                    songEntity -> Song(
                    id = songEntity.id,
                    title = songEntity.title,
                    artists = songEntity.song_artists
                        .map{
                            songArtistEntity ->  Artist(id = songArtistEntity.artist.id, name = songArtistEntity.artist.name)
                        },
                    album = songEntity.album.title,
                    image = songEntity.album.image,
                    duration = songEntity.duration)
            }
            return Playlist(
                id = playlistEntity.id,
                title = playlistEntity.title,
                subtitle = playlistEntity.subtitle,
                image = playlistEntity.image,
                songs = songList
            )
        } else throw PlaylistNotFoundException()
    }
}

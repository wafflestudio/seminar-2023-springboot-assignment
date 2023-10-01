package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl (
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        val playlistGroupEntities = playlistGroupRepository.findAllWithPlaylists()
        return playlistGroupEntities.map { grEntity ->
            PlaylistGroup(
                id = grEntity.id,
                title = grEntity.title,
                // grEntity의 playlists는 PlaylistEntity의 List이므로 Brief DTO로의 변환이 필요함
                playlists = grEntity.playlists.map { plEntity->
                    PlaylistBrief( id = plEntity.id,
                        title = plEntity.title,
                        subtitle = plEntity.subtitle,
                        image = plEntity.image )
                }.toList()
            )
        }
    }

    override fun get(id: Long): Playlist {
        val playlistEntity = playlistRepository.findByIdWithSongs(id = id) ?: throw PlaylistNotFoundException()
        val playlistSongEntities = playlistEntity.playlistSongs
        val songEntities = songRepository.findSongEntitiesByIdsWithArtists(playlistSongEntities.map{ plsEntity -> plsEntity.id}.toList())
        return Playlist(id = playlistEntity.id,
            title = playlistEntity.title,
            subtitle = playlistEntity.subtitle,
            image = playlistEntity.image,
            songs = songEntities.map { sEntity->
                Song(id = sEntity.id,
                    title = sEntity.title,
                    artists = sEntity.songArtists.map { saEntity->
                        val aArtist = saEntity.artist
                        Artist(id= aArtist.id,
                            name = aArtist.name)
                    }.toList(),
                    // 여기서 한번더 inner join 필요할듯?
                    album = sEntity.album.title,
                    image = sEntity.album.image,
                    //왜 문자열이지??
                    duration = sEntity.duration.toString()
                )}.toList()
                )
    }
}

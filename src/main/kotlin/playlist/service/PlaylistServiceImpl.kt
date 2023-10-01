package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository
) : PlaylistService {
    /**
     * 오픈 상태의 플리 그룹 조회.
     * 연관된 플리 없는 경우 -> 결과에서 제외
     */
    override fun getGroups(): List<PlaylistGroup> {
        val playlistGroupEntities = playlistGroupRepository.findAllOpenWithJoinFetch()

        return playlistGroupEntities
            .map { PlaylistGroup(
                id = it.id, title = it.title, playlists = it.playlists.map { it2 -> PlaylistBrief(
                    id = it2.id, title = it2.title, subtitle = it2.subtitle, image = it2.image
                ) }) }
            .filter { it.playlists.isNotEmpty() }
    }

    /**
     * id에 해당하는 플레이리스트 조회
     */
    override fun get(id: Long): Playlist {
        val playlistEntity = playlistRepository.findPlaylistAndSongsById(id) ?: throw PlaylistNotFoundException()

        val songIds:List<Long> = playlistEntity.playlistSongs.map { it.song.id }

        val songEntities = songRepository.findBySongIdsWithJoinFetch(songIds)

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

package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl (
        private val playlistRepository : PlaylistRepository,
        private val playlistGroupRepository: PlaylistGroupRepository,
        private val songRepository: SongRepository
): PlaylistService {
    override fun getGroups(): List<PlaylistGroup> {
        return playlistGroupRepository
                .getByOpenWithJoinFetch(true)
                .filter{ it.playlists.isNotEmpty() }
                .map{it.toPlaylistGroup()}
    }

    override fun get(id: Long): Playlist {
        val playlistEntity = playlistRepository.findByIdWithJoinFetch(id) ?: throw PlaylistNotFoundException()
        val songIds = playlistEntity.playlistSongs.map{it.song.id}
        val songs = songRepository.findByIdInWithJoinFetch(songIds).map{it.toSong()}
        return Playlist(
                id = playlistEntity.id,
                title = playlistEntity.title,
                subtitle = playlistEntity.subtitle,
                image = playlistEntity.image,
                songs = songs.sortedBy{it.id}
        )
    }
}

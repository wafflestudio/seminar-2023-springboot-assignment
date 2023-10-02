package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.*
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import com.wafflestudio.seminar.spring2023.song.service.SongService
import com.wafflestudio.seminar.spring2023.song.service.SongServiceImpl.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
        private val playlistGroupRepository: PlaylistGroupRepository,
        private val playlistRepository: PlaylistRepository,
        private val songService: SongService
) : PlaylistService {
    override fun getGroups(): List<PlaylistGroup> {
        return playlistGroupRepository.findByOpen().map { toGroup(it) }
    }
    override fun get(id: Long): Playlist {
        val pEntity = playlistRepository.findByplId(id) ?: throw PlaylistNotFoundException()
        return Playlist(
                id = pEntity.id,
                image = pEntity.image,
                title = pEntity.title,
                subtitle = pEntity.subtitle,
                songs = pEntity.playlistSongs.map{ songService.toSong(it.song) }.sortedBy { it.id }
        )
    }
    fun toGroup(groupEntity: PlaylistGroupEntity) : PlaylistGroup =
            PlaylistGroup(
                    id = groupEntity.id,
                    title = groupEntity.title,
                    playlists = groupEntity.playlist.map { it -> toPlaylistBrief(it) })
    fun toPlaylistBrief(playlistEntity: PlaylistEntity) : PlaylistBrief =
            PlaylistBrief(
                    id = playlistEntity.id,
                    image = playlistEntity.image,
                    title = playlistEntity.title,
                    subtitle = playlistEntity.subtitle)
}


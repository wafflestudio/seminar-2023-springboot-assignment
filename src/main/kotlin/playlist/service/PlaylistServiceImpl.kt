package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.*
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
        return playlistGroupRepository.findByOpen().map {
            it ->
            toGroup(it)
        };
    }

    override fun get(id: Long): Playlist {
        val pEntity = playlistRepository.findByplId(id) ?: throw PlaylistNotFoundException()
        return pEntitytoPlaylist(pEntity)
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
    fun toPlaylist(playlistSongsEntity: PlaylistSongsEntity) : Playlist =
            Playlist(
                    id = playlistSongsEntity.playlist.id,
                    image = playlistSongsEntity.playlist.image,
                    title = playlistSongsEntity.playlist.title,
                    subtitle = playlistSongsEntity.playlist.subtitle,
                    songs = playlistSongsEntity.playlist.playlistSongs.map{
                        it ->
                        songService.toSong(it.song)
                    }
            )
    fun pEntitytoPlaylist(playlistEntity: PlaylistEntity) : Playlist =
            Playlist(
                    id = playlistEntity.id,
                    image = playlistEntity.image,
                    title = playlistEntity.title,
                    subtitle = playlistEntity.subtitle,
                    songs = playlistEntity.playlistSongs.map{
                        it ->
                        songService.toSong(it.song)
                    }
            )
}


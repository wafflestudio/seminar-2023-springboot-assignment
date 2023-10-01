package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.*
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.service.SongService
import com.wafflestudio.seminar.spring2023.song.service.SongServiceImpl.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
        private val playlistGroupRepository: PlaylistGroupRepository,
        private val playlistRepository: PlaylistRepository,
        private val playlistSongsRepository: PlaylistSongsRepository,
        private val songService: SongService
) : PlaylistService {
    override fun getGroups(): List<PlaylistGroup> {
        return playlistGroupRepository.findByOpen().map { toGroup(it) };
    }

    override fun get(id: Long): Playlist {
        //val pEntity = playlistRepository.findByplId(id) ?: throw PlaylistNotFoundException()
        val psEntity = playlistSongsRepository.findByPlaylistId(id)
        if (psEntity.isEmpty()) throw PlaylistNotFoundException()
        val songList = psEntity.map{it.song}
        val playlist = psEntity.get(0).playlist
        return Playlist(
                id = playlist.id,
                image = playlist.image,
                title = playlist.title,
                subtitle = playlist.subtitle,
                songs = songList.map{songService.toSong(it)}.sortedBy { it.id }
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
    fun toPlaylist(playlistSongsEntity: PlaylistSongsEntity) : Playlist =
            Playlist(
                    id = playlistSongsEntity.playlist.id,
                    image = playlistSongsEntity.playlist.image,
                    title = playlistSongsEntity.playlist.title,
                    subtitle = playlistSongsEntity.playlist.subtitle,
                    songs = playlistSongsEntity.playlist.playlistSongs.map{
                        it ->
                        songService.toSong(it.song)
                    }.sortedBy { it.id }
            )
    fun pEntitytoPlaylist(playlistEntity: PlaylistEntity, songList : List<SongEntity>) : Playlist =
            Playlist(
                    id = playlistEntity.id,
                    image = playlistEntity.image,
                    title = playlistEntity.title,
                    subtitle = playlistEntity.subtitle,
                    songs = songList.map { songService.toSong(it) }.sortedBy { it.id }
            )
}


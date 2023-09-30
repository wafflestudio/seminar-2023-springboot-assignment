package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.song.service.Song


data class Playlist(
        val id: Long,
        val title: String,
        val subtitle: String,
        val image: String,
        val songs: List<Song>,
)

fun Playlist(playlistEntity: PlaylistEntity) = Playlist(
    id = playlistEntity.id,
    title = playlistEntity.title,
    subtitle = playlistEntity.subtitle,
    image = playlistEntity.image,
    songs = playlistEntity.songList.map { songEntity -> Song(songEntity) }.sortedBy { it.id }
)
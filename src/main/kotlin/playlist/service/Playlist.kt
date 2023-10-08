package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.song.service.Song
import com.wafflestudio.seminar.spring2023.song.service.toSong

data class Playlist(
    val id: Long,
    val title: String,
    val subtitle: String,
    val image: String,
    val songs: List<Song>,
)

fun PlaylistEntity.toPlaylist() = Playlist(
    id = id,
    title = title,
    subtitle = subtitle,
    image = image,
    songs = playlistSongs.map { it.song.toSong() },
)

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
    songs = playlistEntity.playlist_songs.map(::Song).sortedBy { it.id },
)
//playlistentity 내부의 playlist_songsentity가 list<song>으로 변해야함
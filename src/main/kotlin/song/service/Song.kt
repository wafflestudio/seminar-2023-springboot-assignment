package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongsEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity

data class Song(
    val id: Long,
    val title: String,
    val artists: List<Artist>,
    val album: String,
    val image: String,
    val duration: Int,
)

fun Song(playlistSongsEntity: PlaylistSongsEntity) = Song(
        id = playlistSongsEntity.song.id,
        title = playlistSongsEntity.song.title,
        artists = playlistSongsEntity.song.song_artists.map(::Artist).sortedBy { it.id },
        album = playlistSongsEntity.song.album.title,
        image = playlistSongsEntity.song.album.image,
        duration = playlistSongsEntity.song.duration,
)
// playlist_songsentity가 song으로 변해야함
//여기서 playlist_songentity 내부 song(songentity)
fun Song(songEntity: SongEntity) = Song(
    id = songEntity.id,
    title = songEntity.title,
    artists = songEntity.song_artists.map(::Artist).sortedBy { it.id },
    album = songEntity.album.title,
    image = songEntity.album.image,
    duration = songEntity.duration,
)
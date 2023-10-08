package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity

data class Song(
    val id: Long,
    val title: String,
    val artists: List<Artist>,
    val album: String,
    val image: String,
    val duration: String,
)

fun SongEntity.toSong() = Song(
    id = id,
    title = title,
    artists = songArtists.map { it.artist.toArtist() },
    album = album.title,
    image = album.image,
    duration = duration.toString(),
)

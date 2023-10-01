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

fun SongEntity.toSong(): Song = Song(
    id = id,
    title = title,
    artists = artists.map { it.artist.toArtist() },
    image = album.image,
    album = album.title,
    duration = "${duration / 60}:${String.format("%02d", duration % 60)}",
)

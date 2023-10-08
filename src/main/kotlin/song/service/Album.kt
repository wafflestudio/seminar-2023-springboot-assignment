package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity

data class Album(
    val id: Long,
    val title: String,
    val image: String,
    val artist: Artist,
)

fun AlbumEntity.toAlbum() = Album(
    id = id,
    title = title,
    image = image,
    artist = artist.toArtist(),
)

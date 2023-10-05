package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity

data class Album(
    val id: Long,
    val title: String,
    val image: String,
    val artist: Artist,
)

fun Album(albumEntity: AlbumEntity) = Album(
    id = albumEntity.id,
    title = albumEntity.title,
    image = albumEntity.image,
    artist = Artist(albumEntity.artist),
)
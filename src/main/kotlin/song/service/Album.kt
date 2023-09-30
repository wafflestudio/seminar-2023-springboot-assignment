package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity

data class Album(
    val id: Long,
    val title: String,
    val image: String,
    val artist: Artist,
)

fun AlbumEntity.toAlbum(): Album {
    return Album(
            id = this.id,
            title = this.title,
            image = this.image,
            artist = this.artist.toArtist()
    )
}

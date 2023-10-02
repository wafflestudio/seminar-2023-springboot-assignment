package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity

data class Album(
    val id: Long,
    val title: String,
    val image: String,
    val artist: Artist,
) {
    constructor(entity: AlbumEntity): this(
        id = entity.id,
        title = entity.title,
        image = entity.image,
        artist = Artist(entity.artist)
    )
}

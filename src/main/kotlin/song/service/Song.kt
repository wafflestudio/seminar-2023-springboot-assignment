package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity

data class Song(
    val id: Long,
    val title: String,
    val artists: List<Artist>,
    val album: String,
    val image: String,
    val duration: String,
) {
    constructor(entity: SongEntity): this(
        id = entity.id,
        title = entity.title,
        artists = entity.artists.map { Artist(it.artist) },
        album = entity.album.title,
        image = entity.album.image,
        duration = entity.duration
    )
}

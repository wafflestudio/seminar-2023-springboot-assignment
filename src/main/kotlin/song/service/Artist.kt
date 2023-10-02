package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongArtistEntity

data class Artist(
    val id: Long,
    val name: String,
)

fun SongArtistEntity.toArtist() : Artist {
    return Artist(
            id = this.artist.id,
            name = this.artist.name
    )
}
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

fun SongEntity.toSong(): Song {
    return Song(
        id = this.id,
        title = this.title,
        artists = this.songArtists.map { it.toArtist() },
        album = this.album.title,
        image = this.album.image,
        duration = this.duration,
    )
}

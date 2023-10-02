package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongArtistEntity

data class Song(
    val id: Long,
    val title: String,
    val artists: List<Artist>,
    val album: String,
    val image: String,
    val duration: String,
)


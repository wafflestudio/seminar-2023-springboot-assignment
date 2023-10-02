package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.song.service.Song

data class Playlist(
    val id: Long,
    val title: String,
    val subtitle: String,
    val image: String,
    val songs: List<Song>,
)

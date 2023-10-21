package com.wafflestudio.seminar.spring2023.customplaylist.service

import com.wafflestudio.seminar.spring2023.song.service.Song

data class CustomPlaylist(
    val id: Long,
    val title: String,
    val songs: List<Song>
)

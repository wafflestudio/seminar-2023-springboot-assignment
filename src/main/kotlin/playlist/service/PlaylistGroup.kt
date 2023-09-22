package com.wafflestudio.seminar.spring2023.playlist.service

data class PlaylistGroup(
    val id: Long,
    val title: String,
    val playlists: List<PlaylistBrief>,

)

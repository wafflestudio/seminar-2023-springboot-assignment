package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupEntity

data class PlaylistGroup(
    val id: Long,
    val title: String,
    val playlists: List<PlaylistBrief>,
)

fun PlaylistGroupEntity.toPlaylistGroup() = PlaylistGroup(
    id = id,
    title = title,
    playlists = playlists.map { it.toPlaylistBrief() },
)

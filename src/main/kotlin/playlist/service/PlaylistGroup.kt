package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupEntity

data class PlaylistGroup(
    val id: Long,
    val title: String,
    val playlists: List<PlaylistBrief>,
)

//convert PlaylistGroup entity to PlaylistGroup
fun PlaylistGroup(playlistGroupEntity: PlaylistGroupEntity) = PlaylistGroup(
        id = playlistGroupEntity.id,
        title = playlistGroupEntity.title,
        playlists = playlistGroupEntity.playlists.map(::PlaylistBrief).sortedBy { it.id },
)
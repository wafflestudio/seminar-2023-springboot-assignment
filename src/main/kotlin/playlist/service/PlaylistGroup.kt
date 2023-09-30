package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupEntity

data class PlaylistGroup(
    val id: Long,
    val title: String,
    val playlists: List<PlaylistBrief>,
)

fun PlaylistGroup(playlistGroupEntity: PlaylistGroupEntity) = PlaylistGroup(
    id = playlistGroupEntity.id,
    title = playlistGroupEntity.title,
    playlists = playlistGroupEntity.playlistList.map {
        playlistEntity -> PlaylistBrief(
            id = playlistEntity.id,
            title = playlistEntity.title,
            subtitle = playlistEntity.subtitle,
            image = playlistEntity.image
        )
    }
)

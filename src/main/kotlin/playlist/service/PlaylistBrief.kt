package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity

data class PlaylistBrief(
    val id: Long,
    val title: String,
    val subtitle: String,
    val image: String,
)

fun PlaylistBrief(playlistEntity: PlaylistEntity) = PlaylistBrief(
        id = playlistEntity.id,
        title = playlistEntity.title,
        subtitle = playlistEntity.subtitle,
        image = playlistEntity.image
)
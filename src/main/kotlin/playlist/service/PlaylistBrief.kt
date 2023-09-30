package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity

data class PlaylistBrief(
    val id: Long,
    val title: String,
    val subtitle: String,
    val image: String,
)

fun PlaylistEntity.toPlaylistBrief(): PlaylistBrief {
    return PlaylistBrief(
            id = this.id,
            title = this.title,
            subtitle = this.subtitle,
            image = this.image
    )
}

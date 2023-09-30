package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupEntity

data class PlaylistGroup(
    val id: Long,
    val title: String,
    val playlists: List<PlaylistBrief>,
)

fun PlaylistGroupEntity.toPlayListGroup(): PlaylistGroup {
    return PlaylistGroup(
            id = this.id,
            title = this.title,
            playlists = this.playlists.map { it.toPlaylistBrief() }
    )
}

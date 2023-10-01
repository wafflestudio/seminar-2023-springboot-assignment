package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupEntity

data class PlaylistGroup(
    val id: Long,
    val title: String,
    val playlists: List<PlaylistBrief>,
) {
    constructor(entity: PlaylistGroupEntity): this(
        id = entity.id,
        title = entity.title,
        playlists = entity.playlists.map {
            PlaylistBrief(it)
        }
    )
}

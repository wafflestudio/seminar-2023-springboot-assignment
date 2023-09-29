package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.song.service.Song
import com.wafflestudio.seminar.spring2023.song.service.toSong

data class Playlist(
    val id: Long,
    val title: String,
    val subtitle: String,
    val image: String,
    val songs: List<Song>,
) {

    constructor(entity: PlaylistEntity) : this(
        id = entity.id,
        title = entity.title,
        subtitle = entity.subtitle,
        image = entity.image,
        songs = entity.songs.map { it.song.toSong() }
    )

}
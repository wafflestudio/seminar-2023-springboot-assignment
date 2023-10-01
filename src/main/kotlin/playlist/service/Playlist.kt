package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.service.Song
import com.wafflestudio.seminar.spring2023.song.service.toSong

data class Playlist(
    val id: Long,
    val title: String,
    val subtitle: String,
    val image: String,
    val songs: List<Song>,
) {

    constructor(playlistEntity: PlaylistEntity, songEntities: List<SongEntity>) : this(
        id = playlistEntity.id,
        title = playlistEntity.title,
        subtitle = playlistEntity.subtitle,
        image = playlistEntity.image,
        songs = songEntities.map(SongEntity::toSong)
    )

}
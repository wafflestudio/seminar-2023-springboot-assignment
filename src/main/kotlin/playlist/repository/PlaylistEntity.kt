package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.Playlist
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistBrief
import com.wafflestudio.seminar.spring2023.song.repository.*
import com.wafflestudio.seminar.spring2023.song.service.Song
import jakarta.persistence.*
import java.util.*

@Entity(name = "playlists")
class PlaylistEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title : String,
    val subtitle : String,
    val image : String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id",nullable = false)
    val group : PlaylistGroupEntity,

    @OneToMany(mappedBy = "playlist")
    val playlistSongs : List<PlaylistSongEntity>,

    @OneToMany(mappedBy = "playlist")
    val playlist_likes : List<PlaylistLikeEntity>,

    )

fun Playlist(entity: Optional<PlaylistEntity>, songEntityList :List<SongEntity>) : Playlist{
    val songList = songEntityList.map { Song(it) }
    return Playlist(
        id = entity.get().id,
        title = entity.get().title,
        subtitle = entity.get().subtitle,
        image = entity.get().image,
        songs = songList
    )
}

fun PlaylistBrief(entity: PlaylistEntity) : PlaylistBrief{
    return PlaylistBrief(
        id = entity.id,
        title = entity.title,
        subtitle = entity.subtitle,
        image = entity.image
    )
}
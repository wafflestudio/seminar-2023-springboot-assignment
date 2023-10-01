package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongEntity
import jakarta.persistence.*

class SongEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title : String,
    val duration : Int = 0,
    @OneToMany(mappedBy = "song")
    val playlistSongs: MutableList<PlaylistSongEntity> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    val album: AlbumEntity,

    @OneToMany(mappedBy = "song")
    val songArtists: MutableList<SongArtistEntity> = mutableListOf()
    )
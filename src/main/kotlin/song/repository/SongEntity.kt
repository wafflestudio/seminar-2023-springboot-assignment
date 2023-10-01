package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongEntity
import jakarta.persistence.*

@Entity(name = "songs")
class SongEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val duration: String,
    @ManyToOne
    @JoinColumn(name = "album_id")
    val album: AlbumEntity,
    @OneToMany(mappedBy = "song")
    val artists: List<SongArtistEntity>,
    @OneToMany(mappedBy = "song")
    val playlists: List<PlaylistSongEntity>,
)
package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongEntity
import jakarta.persistence.*

@Entity(name = "songs")
class SongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val duration: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    val album: AlbumEntity,

    @OneToMany(mappedBy = "song")
    val songArtists: List<SongArtistEntity> = mutableListOf(),

    @OneToMany(mappedBy = "song")
    val playlistSongs: List<PlaylistSongEntity> = mutableListOf()
)
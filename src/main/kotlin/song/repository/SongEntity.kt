package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongsEntity
import jakarta.persistence.*

@Entity(name = "songs")
class SongEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val title: String,
    val duration: Int,

    @ManyToOne
    @JoinColumn(name = "album_id")
    val album: AlbumEntity,

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
    val song_artists: Set<SongArtistsEntity>,

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
    val playlist_songs: Set<PlaylistSongsEntity>
)
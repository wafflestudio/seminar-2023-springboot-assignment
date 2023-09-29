package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Artist
import jakarta.persistence.*

@Entity(name = "songs")
class SongEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val duration: String,

    @ManyToOne
    @JoinColumn(name = "album_id", referencedColumnName = "id")
    val album: AlbumEntity,

    @ManyToMany
    @JoinTable(
        name = "song_artists",
        joinColumns = [JoinColumn(name = "song_id")],
        inverseJoinColumns = [JoinColumn(name = "artist_id")]
    )
    val artists: List<ArtistEntity>
)
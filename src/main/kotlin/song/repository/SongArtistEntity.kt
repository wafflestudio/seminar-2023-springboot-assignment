package com.wafflestudio.seminar.spring2023.song.repository

import jakarta.persistence.*

class SongArtistEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne
    @JoinColumn(name = "artist_id")
    val artist: ArtistEntity,

    @ManyToOne
    @JoinColumn(name = "song_id")
    val song: SongEntity
)
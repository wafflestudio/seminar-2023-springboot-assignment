package com.wafflestudio.seminar.spring2023.song.repository

import jakarta.persistence.*

@Entity(name = "song_artists")
class SongArtistsEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        val id: Long = 0L,
        @ManyToOne
        @JoinColumn(name = "artist_id", nullable = false)
        val artist: ArtistEntity,
        @ManyToOne
        @JoinColumn(name = "song_id", nullable = false)
        val song: SongEntity,
)
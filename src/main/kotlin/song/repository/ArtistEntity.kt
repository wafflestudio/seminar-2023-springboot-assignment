package com.wafflestudio.seminar.spring2023.song.repository

import jakarta.persistence.*

@Entity(name = "artists")
class ArtistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String,
    @OneToMany(mappedBy = "artist")
    val albums: List<AlbumEntity>,
    @OneToMany(mappedBy = "artist")
    val songs: List<SongArtistEntity>,
)
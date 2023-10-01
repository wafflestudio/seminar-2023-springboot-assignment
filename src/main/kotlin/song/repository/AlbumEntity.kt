package com.wafflestudio.seminar.spring2023.song.repository

import jakarta.persistence.*

@Entity(name = "albums")
class AlbumEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val image: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id")
    val artist: ArtistEntity,

    @OneToMany(mappedBy = "album")
    val songs : List<SongEntity>
)
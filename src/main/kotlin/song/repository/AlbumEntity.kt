package com.wafflestudio.seminar.spring2023.song.repository

import jakarta.persistence.*

@Entity(name = "albums")
class AlbumEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0L,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val image: String,
    @ManyToOne // default FetchType.EAGER
    @JoinColumn(name = "artist_id", nullable = false)
    val artist: ArtistEntity,
    @OneToMany(mappedBy = "album")
    val songs: List<SongEntity>,
)

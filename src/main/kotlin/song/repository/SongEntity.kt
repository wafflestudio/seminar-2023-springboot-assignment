package com.wafflestudio.seminar.spring2023.song.repository

import jakarta.persistence.*

@Entity(name = "songs")
class SongEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val duration: String,
    @ManyToOne // default FetchType.EAGER
    @JoinColumn(name = "album_id")
    val album: AlbumEntity,
    @OneToMany(mappedBy = "song")
    val song_artists: List<SongArtistEntity>,
)
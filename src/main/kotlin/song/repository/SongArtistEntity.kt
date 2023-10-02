package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Song
import jakarta.persistence.*

@Entity(name = "song_artists")
class SongArtistEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne // default FetchType.EAGER
    @JoinColumn(name = "artist_id")
    val artist: ArtistEntity,
    @ManyToOne // default FetchType.EAGER
    @JoinColumn(name = "song_id")
    val song: SongEntity,
    )
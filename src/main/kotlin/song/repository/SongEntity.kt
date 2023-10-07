package com.wafflestudio.seminar.spring2023.song.repository

import jakarta.annotation.Nonnull
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "songs")
class SongEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    @Nonnull
    val duration: Int = 0,
    @ManyToOne
    @JoinColumn(name = "album_id")
    val album: AlbumEntity,
)

class SongArtistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne
    @JoinColumn(name = "song_id")
    val song: SongEntity,
    @ManyToOne
    @JoinColumn(name = "artist_id")
    val artist: ArtistEntity,
)

package com.wafflestudio.seminar.spring2023.song.repository

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity(name = "songs")
class SongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val duration: Int = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    val album: AlbumEntity,
    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
    val artists: List<SongArtistEntity>,
)

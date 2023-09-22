package com.wafflestudio.seminar.spring2023.song.repository

import jakarta.persistence.*

@Entity(name = "songs")
class SongEntity(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val duration: Int,

    @ManyToOne
    @JoinColumn(name = "album_id")
    val album: AlbumEntity
)
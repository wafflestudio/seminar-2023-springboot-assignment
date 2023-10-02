package com.wafflestudio.seminar.spring2023.song.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity(name = "albums")
class AlbumEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val image: String,
    @ManyToOne // default FetchType.EAGER
    @JoinColumn(name = "artist_id")     //여러앨범1아티스트
    val artist: ArtistEntity,
    @OneToMany(mappedBy = "album")
    val songs: List<SongEntity>,

    )

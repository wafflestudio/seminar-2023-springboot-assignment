package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Artist
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "artists")
class ArtistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String,

    @OneToMany(mappedBy = "artist")
    val albums: List<AlbumEntity>,

    @OneToMany(mappedBy = "artist")
    val songArtists : List<SongArtistEntity>,
)

fun Artist(entity : ArtistEntity) : Artist {
    return Artist(
        id = entity.id,
        name = entity.name,
    )
}
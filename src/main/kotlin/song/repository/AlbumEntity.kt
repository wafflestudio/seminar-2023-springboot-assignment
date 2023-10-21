package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Artist
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
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

    @ManyToOne(fetch = FetchType.EAGER) // default FetchType.EAGER
    @JoinColumn(name = "artist_id",nullable = false)
    val artist : ArtistEntity,

    @OneToMany(mappedBy = "album")
    val songs : List<SongEntity>

)

fun Album(entity: AlbumEntity) : Album {
    return Album(
        id = entity.id,
        title = entity.title,
        image = entity.image,
        artist = Artist(entity.artist),
    )
}

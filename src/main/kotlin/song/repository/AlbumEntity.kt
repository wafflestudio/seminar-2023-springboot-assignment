package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Album
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
    @ManyToOne // default FetchType.EAGER
    @JoinColumn(name = "artist_id")
    val artist: ArtistEntity,
    @OneToMany
    val songs: List<SongEntity>,
)
{
    fun toAlbum(): Album {
        return Album(
                id = this.id,
                title = this.title,
                image = this.image,
                artist = this.artist.toArtist()
        )
    }
}

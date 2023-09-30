package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Artist
import jakarta.persistence.*
import java.awt.Image

@Entity(name = "songs")
class SongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val duration: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "song_artists",
        joinColumns = [JoinColumn(name = "song_id")],
        inverseJoinColumns = [JoinColumn(name = "artist_id")]
    )
    val artists: List<ArtistEntity>,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    val album: AlbumEntity,

    @Transient
    var image: String
) {
    init {
        image = album.image
    }
}





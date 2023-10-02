package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import jakarta.persistence.*

@Entity(name = "song_artists")
class SongArtistEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id",nullable = false)
    val artist: ArtistEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id",nullable = false)
    val song: SongEntity,
)

fun getArtistList(entityList: List<SongArtistEntity>) : List<Artist>{
    val artistList = mutableListOf<Artist>()
    entityList.forEach { songArtistEntity ->
        artistList.add(Artist(songArtistEntity.artist))
    }
    return artistList;
}
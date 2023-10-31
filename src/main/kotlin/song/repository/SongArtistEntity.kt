package com.wafflestudio.seminar.spring2023.song.repository

<<<<<<< HEAD
import jakarta.persistence.*
=======
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1

@Entity(name = "song_artists")
class SongArtistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
<<<<<<< HEAD
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    val artist : ArtistEntity,
    @ManyToOne
    @JoinColumn(name = "song_id")
    val song : SongEntity,
)
=======
    @ManyToOne
    @JoinColumn(name = "song_id")
    val song: SongEntity,
    @ManyToOne
    @JoinColumn(name = "artist_id")
    val artist: ArtistEntity,
)
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1

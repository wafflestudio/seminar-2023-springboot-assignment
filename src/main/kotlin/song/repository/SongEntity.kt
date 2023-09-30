package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongsEntity
import jakarta.persistence.*

@Entity(name = "songs")
class SongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0L,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val duration: Int,
    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    val album: AlbumEntity,
    @OneToMany(mappedBy = "song")
    val song_artists: List<SongArtistsEntity>,
    @OneToMany(mappedBy = "song")
    val playlist: List<PlaylistSongsEntity>,
)
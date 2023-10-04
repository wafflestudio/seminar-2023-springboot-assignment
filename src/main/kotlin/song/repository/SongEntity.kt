package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.hibernate.annotations.BatchSize

@Entity(name = "songs")
class SongEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val duration: String,
    @ManyToOne // default FetchType.EAGER
    @JoinColumn(name = "album_id")
    val album: AlbumEntity,
    @OneToMany(mappedBy = "song")
    @BatchSize(size = 100)
    val song_artists: List<SongArtistEntity>,
    @OneToMany(mappedBy = "song")
    val playlist_songs: List<PlaylistSongEntity>,
)
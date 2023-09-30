package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity(name = "songs")
class SongEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,
        val title: String,
        val duration: String,
        @OneToMany(mappedBy = "song")
        val playlistSongs: List<PlaylistSongEntity>,
        @OneToMany(mappedBy = "song")
        val songArtists: List<SongArtistEntity>,
        @ManyToOne
        @JoinColumn(name = "album_id")
        val album: AlbumEntity,
)
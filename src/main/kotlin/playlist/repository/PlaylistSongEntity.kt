package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "playlistSongs")
class PlaylistSongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    val playlist: PlaylistEntity,

    @ManyToOne
    @JoinColumn(name = "song_id")
    val song: SongEntity,


)
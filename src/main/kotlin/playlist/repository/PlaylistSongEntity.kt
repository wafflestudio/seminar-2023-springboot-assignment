package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import jakarta.persistence.*

@Entity(name = "playlist_songs")
class PlaylistSongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id",nullable = false)
    val song : SongEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id",nullable = false)
    val playlist : PlaylistEntity,

)
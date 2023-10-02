package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import jakarta.persistence.*

@Entity(name = "playlist_songs")
class PlaylistSongsEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        val id: Long = 0L,
        @ManyToOne
        @JoinColumn(name = "playlist_id", nullable = false)
        val playlist: PlaylistsEntity,
        @ManyToOne
        @JoinColumn(name = "song_id", nullable = false)
        val song: SongEntity,
)
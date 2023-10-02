package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import jakarta.persistence.*

@Entity(name = "playlist_songs")
class PlaylistSongEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne // default FetchType.EAGER
    @JoinColumn(name = "playlist_id")
    val playlist: PlaylistEntity,
    @ManyToOne // default FetchType.EAGER
    @JoinColumn(name = "song_id")
    val song: SongEntity,
)
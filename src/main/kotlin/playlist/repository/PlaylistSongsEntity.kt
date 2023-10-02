package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import jakarta.persistence.*
import org.springframework.context.annotation.Configuration

@Configuration
@Entity(name = "playlist_songs")
class PlaylistSongsEntity (
    @Id
    @GeneratedValue
    val id: Long = 0L,
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    val playlist: PlaylistEntity,
    @ManyToOne
    @JoinColumn(name = "song_id")
    val song: SongEntity,
)
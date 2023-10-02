package com.wafflestudio.seminar.spring2023.playlist.repository
import com.wafflestudio.seminar.spring2023.song.repository.SongArtistsEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Column
import jakarta.persistence.ManyToOne

@Entity(name = "playlist_songs")
class PlaylistSongsEntity (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,
        @ManyToOne (fetch = FetchType.EAGER)
        @JoinColumn(name = "playlist_id")
        val playlist: PlaylistEntity,
        @ManyToOne (fetch = FetchType.EAGER)
        @JoinColumn(name = "song_id")
        val song: SongEntity,
)
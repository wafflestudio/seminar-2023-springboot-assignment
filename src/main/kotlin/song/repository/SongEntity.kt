package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import jakarta.persistence.*

@Entity(name = "songs")
class SongEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,
    val duration: Int,

    @ManyToOne
    @JoinColumn(name = "album_id")
    val album: AlbumEntity,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "playlist_songs",
            joinColumns = [JoinColumn(name = "song_id")],
            inverseJoinColumns = [JoinColumn(name = "playlist_id")]
    )
    val playlistList: List<PlaylistEntity>,

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
    val songArtistRelationshipList: List<SongArtistRelationshipEntity>
)
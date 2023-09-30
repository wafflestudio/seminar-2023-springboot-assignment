package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity
import jakarta.persistence.*


@Entity(name = "playlists")
class PlaylistEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val subtitle: String,
    val image: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    val group: PlaylistGroupEntity,
    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY)
    val playlist_songs: List<PlaylistSongEntity>,
    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY)
    val playlist_likes: List<PlaylistLikeEntity>,

)
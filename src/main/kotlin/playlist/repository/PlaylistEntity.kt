package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.persistence.*

@Entity(name = "playlists")
class PlaylistEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val subtitle: String,
    val image: String,

    @ManyToOne // default FetchType.EAGER
    @JoinColumn(name = "group_id")
    val playlistGroup: PlaylistGroupEntity,

    @OneToMany(mappedBy = "playlist")
    val playlistSong: List<PlaylistSongEntity>,

    @OneToMany(mappedBy = "playlist")
    val playlistLike: List<PlaylistLikeEntity>,
)
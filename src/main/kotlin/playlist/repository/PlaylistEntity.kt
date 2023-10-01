package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.persistence.*

@Entity(name = "playlists")
class PlaylistEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val title: String,
    val subtitle: String,
    val image: String,

    @OneToMany(mappedBy = "playlist")
    val playlistLikes: List<PlaylistLikesEntity>,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    val group: PlaylistGroupEntity,

    @OneToMany(mappedBy = "playlist")
    val playlistSongs: List<PlaylistSongEntity>
)
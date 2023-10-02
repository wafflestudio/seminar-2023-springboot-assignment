package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.persistence.*

@Entity(name = "playlists")
class PlaylistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val subtitle: String,
    val image: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    val group: PlaylistGroupEntity,
    @OneToMany(mappedBy = "playlist")
    val playlistSongs: List<PlaylistSongEntity>,
    @OneToMany(mappedBy = "playlist")
    val playlistLikes: List<PlaylistLikeEntity>
)
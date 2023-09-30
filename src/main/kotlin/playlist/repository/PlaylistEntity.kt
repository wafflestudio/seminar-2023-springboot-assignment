package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.persistence.*

@Entity(name = "playlists")
class PlaylistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val subtitle: String,

    @Column(nullable = false)
    val image: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    val group: PlaylistGroupEntity,

    @OneToMany(mappedBy = "playlist")
    val playlistSongs: MutableList<PlaylistSongEntity> = mutableListOf(),

)

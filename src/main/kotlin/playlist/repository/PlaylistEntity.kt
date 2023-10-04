package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.persistence.*

@Entity(name = "playlists")
class PlaylistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,

    val subtitle: String,

    val image: String,

    @ManyToOne
    @JoinColumn(name = "group_id")
    val playlist_group: PlaylistGroupEntity,

    @OneToMany(mappedBy = "playlist")
    val playlist_songs: Set<PlaylistSongsEntity>,

    @OneToMany(mappedBy = "playlist")
    val playlist_likes: Set<PlaylistLikesEntity>
)
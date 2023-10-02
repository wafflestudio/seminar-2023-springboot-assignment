package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.persistence.*

@Entity(name = "playlist_groups")
class PlaylistGroupEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val open: Boolean,
    @OneToMany(mappedBy = "playlistGroup")
    val playlist: List<PlaylistEntity>,
)
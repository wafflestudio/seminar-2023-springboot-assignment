package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistBrief
import jakarta.persistence.*

@Entity(name = "playlist_groups")
class PlaylistGroupEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    //open
    @OneToMany(mappedBy = "playlistGroup")
    val playlists: List<PlaylistEntity>,
)
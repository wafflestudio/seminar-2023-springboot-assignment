package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.service.Song
import jakarta.persistence.*


@Entity(name = "playlists")
class PlaylistEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val subtitle: String,
    val image: String,
    @ManyToOne  //
    @JoinColumn(name = "group_id")
    val playlistGroup: PlaylistGroupEntity,
    @OneToMany(mappedBy = "playlist")
    val songs: List<PlaylistSongsEntity>,   //mutable
    @OneToMany(mappedBy = "playlist")
    val likedUsers: List<PlaylistLikeEntity>,
)
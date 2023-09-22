package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import jakarta.persistence.*

@Entity(name = "playlists")
class PlaylistEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,
    val subtitle: String,
    val image: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    val playlistGroup: PlaylistGroupEntity,

    @ManyToMany(mappedBy = "playlistList", fetch = FetchType.LAZY)
    val songList: List<SongEntity>
)
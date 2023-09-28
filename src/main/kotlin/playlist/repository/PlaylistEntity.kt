package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity(name = "playlists")
class PlaylistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val subtitle: String,
    val image: String,
    @ManyToOne
    @JoinColumn(name = "group_id")
    val playlist_group: PlaylistGroupEntity,
    @OneToMany(mappedBy = "playlistOfSong")
    val playlist_songs: List<PlaylistSongEntity>,
    @OneToMany(mappedBy = "playlistOfLike")
    val playlist_likes: List<PlaylistLikeEntity>,
)
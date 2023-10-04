package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.hibernate.annotations.BatchSize

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
    val playlist_group: PlaylistGroupEntity,
    @OneToMany(mappedBy = "playlist")
    val playlist_likes: List<PlaylistLikeEntity>,
    @OneToMany(mappedBy = "playlist")
    @BatchSize(size = 100)
    val playlist_songs: List<PlaylistSongEntity>,
)
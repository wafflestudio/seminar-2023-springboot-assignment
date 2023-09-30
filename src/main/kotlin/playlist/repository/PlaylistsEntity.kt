package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.persistence.*

@Entity(name = "playlists")
class PlaylistsEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        val id: Long = 0L,
        @Column(nullable = false)
        val title: String,
        @Column(nullable = false)
        val subtitle: String,
        @Column(nullable = false)
        val image: String,
        @ManyToOne
        @JoinColumn(name = "group_id", nullable = false)
        val playlist_group: PlaylistGroupsEntity,
        @OneToMany(mappedBy = "playlist")
        val playlist_songs: List<PlaylistSongsEntity>,
        @OneToMany(mappedBy = "playlist")
        val playlist_likes: List<PlaylistLikesEntity>,
)
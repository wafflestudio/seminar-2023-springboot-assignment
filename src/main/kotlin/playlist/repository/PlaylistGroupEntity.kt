package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

class PlaylistGroupEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id :Long =0L,
    val title: String,
    val open: Boolean,
    @OneToMany(mappedBy = "group")
    val playlists: MutableList<PlaylistEntity> = mutableListOf()


)
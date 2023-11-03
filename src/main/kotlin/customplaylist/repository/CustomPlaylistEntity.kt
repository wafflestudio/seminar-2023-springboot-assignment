package com.wafflestudio.seminar.spring2023.customplaylist.repository

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "custom_playlists")
class CustomPlaylistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val userId: Long,
    var title: String,
    @OneToMany(mappedBy = "customPlaylist", cascade = [CascadeType.ALL], orphanRemoval = true)
    val songs: MutableList<CustomPlaylistSongEntity> = mutableListOf(),
    var songCnt: Int = 0,
)

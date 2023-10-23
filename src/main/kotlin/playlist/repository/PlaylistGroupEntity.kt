package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistBrief
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistGroup
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "playlist_groups")
class PlaylistGroupEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = 0L,
    val title : String,
    val open : Boolean,

    @OneToMany(mappedBy = "group")
    val playlists : List<PlaylistEntity>
)

fun PlaylistGroup(entity :PlaylistGroupEntity) : PlaylistGroup{
    val playlistBriefList = mutableListOf<PlaylistBrief>()
    entity.playlists.forEach { playlistEntity: PlaylistEntity ->
        playlistBriefList.add(PlaylistBrief(playlistEntity))
    }
    return PlaylistGroup(
        id = entity.id,
        title = entity.title,
        playlists = playlistBriefList
    )
}

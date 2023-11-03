package com.wafflestudio.seminar.spring2023.playlist.repository

<<<<<<< HEAD
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistBrief
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistGroup
=======
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

<<<<<<< HEAD

@Entity(name = "playlist_groups")
class PlaylistGroupEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id :Long = 0L,
    val title : String,
    val open : Boolean,
    @OneToMany(mappedBy = "playlistGroup")
    val playlists : List<PlaylistEntity>
){
    fun toPlaylistGroup() : PlaylistGroup{
        val playlistBriefs = playlists.map { it.toPlaylistBrief() }
        return PlaylistGroup(id,title,playlistBriefs)
    }
}
=======
@Entity(name = "playlist_groups")
class PlaylistGroupEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    @OneToMany(mappedBy = "group")
    val playlists: List<PlaylistEntity> = emptyList(),
    val open: Boolean,
)
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1

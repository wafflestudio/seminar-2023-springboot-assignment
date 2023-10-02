package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistBrief
import jakarta.persistence.*

@Entity(name="playlists")
class PlaylistEntity (
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val subtitle: String,
    val image: String,
    @OneToMany(mappedBy = "playlist")
    val playlistSongs: List<PlaylistSongEntity>,
    @OneToMany(mappedBy = "playlist")
    val playlistLikes: List<PlaylistLikeEntity>,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_id")
    val playlistGroup : PlaylistGroupEntity,
) {
    fun toPlaylistBrief() : PlaylistBrief{
        return PlaylistBrief(
                id = this.id,
                title = this.title,
                subtitle = this.subtitle,
                image = this.image
        )
    }
    fun isEmpty(): Boolean {
        return playlistSongs.isEmpty()
    }
}
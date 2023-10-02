package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.playlist.service.Playlist
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistBrief
import com.wafflestudio.seminar.spring2023.song.repository.PlaylistSongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.service.Song
import jakarta.persistence.*

@Entity(name = "playlists")
class PlaylistEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long = 0L,
    val title: String,
    val subtitle: String,
    val image: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    val playlistGroup : PlaylistGroupEntity,
    @OneToMany(mappedBy = "playlist")
    val playlistSongs : List<PlaylistSongEntity>,
    @OneToMany(mappedBy = "playlist")
    val playlistLikes : List<PlaylistLikeEntity>,
){
    fun toPlaylistBrief():PlaylistBrief{
        return PlaylistBrief(id,title,subtitle,image)
    }

    fun toPlaylist():Playlist{
        val songEntities : List<SongEntity> = playlistSongs.map { it.song }
        val songs : List<Song> = songEntities.map { it.toSong() }
        return Playlist(id,title,subtitle,image,songs.sortedBy { it.id })
    }
}
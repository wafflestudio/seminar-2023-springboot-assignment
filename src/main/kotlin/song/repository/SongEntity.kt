package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongEntity
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import jakarta.persistence.*
import java.time.Duration

@Entity(name = "songs")
class SongEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val duration: Int,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id",nullable = false)
    val album : AlbumEntity,

    @OneToMany(mappedBy = "song")
    val songArtists : List<SongArtistEntity>,

    @OneToMany(mappedBy = "song") //"song" <=> playlistSongEntity.song
    val playlistSongs : List<PlaylistSongEntity>,

)
fun Song(entity: SongEntity) : Song {
    return Song(
        id = entity.id,
        title = entity.title,
        artists = getArtistList(entity.songArtists),
        album = entity.album.title,
        image = entity.album.image,
        duration = entity.duration.toString(),
    )
}
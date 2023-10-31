package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import jakarta.persistence.*

@Entity(name = "songs")
class SongEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = 0L,
    val title : String,
    val duration : String,
    @ManyToOne
    @JoinColumn(name = "album_id")
    val album : AlbumEntity,
    @OneToMany(mappedBy = "song")
    val songArtists : List<SongArtistEntity>,
    @OneToMany(mappedBy = "song")
    val playlistSongs : List<PlaylistSongEntity>,
){
    fun toSong() : Song{
        val artistEntities : List<ArtistEntity> = songArtists.map { it.artist }
        val artists : List<Artist> = artistEntities.map { it.toArtist() }
        return Song(id,title,artists,album.title,album.image,duration)
    }
}
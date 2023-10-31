package com.wafflestudio.seminar.spring2023.song.repository

<<<<<<< HEAD
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
=======
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity(name = "songs")
class SongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val duration: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    val album: AlbumEntity,
    @OneToMany(mappedBy = "song", cascade = [CascadeType.ALL])
    val artists: MutableList<SongArtistEntity> = mutableListOf(),
)
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1

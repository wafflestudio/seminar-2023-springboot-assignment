package com.wafflestudio.seminar.spring2023.song.repository

<<<<<<< HEAD
import com.wafflestudio.seminar.spring2023.song.service.Album
=======
import jakarta.persistence.CascadeType
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity(name = "albums")
class AlbumEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val image: String,
<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "artist_id")
    val artist: ArtistEntity,
    @OneToMany(mappedBy = "album")
    val songs : List<SongEntity>,
){
    fun toAlbum():Album{
        return Album(id,title,image,artist.toArtist())
    }
}
=======
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    val artist: ArtistEntity,
    @OneToMany(mappedBy = "album", cascade = [CascadeType.ALL])
    val songs: MutableList<SongEntity> = mutableListOf(),
)
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1

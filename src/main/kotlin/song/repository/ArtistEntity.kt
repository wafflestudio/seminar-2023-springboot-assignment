package com.wafflestudio.seminar.spring2023.song.repository

<<<<<<< HEAD
import com.wafflestudio.seminar.spring2023.song.service.Artist
=======
import jakarta.persistence.CascadeType
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "artists")
class ArtistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String,
<<<<<<< HEAD
    @OneToMany(mappedBy = "artist")
    val albums: List<AlbumEntity>,
){
    fun toArtist():Artist{
        return Artist(id,name)
    }
}
=======
    @OneToMany(mappedBy = "artist", cascade = [CascadeType.ALL])
    val albums: MutableList<AlbumEntity> = mutableListOf(),
)
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1

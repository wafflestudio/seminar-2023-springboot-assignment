package com.wafflestudio.seminar.spring2023.song.repository;

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongEntity
import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity
import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongArtistEntity
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
import com.wafflestudio.seminar.spring2023.song.service.toArtist
import jakarta.persistence.*
import jdk.jfr.Enabled;

@Entity(name = "songs")
public class SongEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val duration: String,
    @ManyToOne
    @JoinColumn(name="album_id")
    val album: AlbumEntity,
    @OneToMany(mappedBy="song")
    val playlistSongs: List<PlaylistSongEntity>,
    @OneToMany(mappedBy="song")
    val songArtists: List<SongArtistEntity>,
) {
    fun toSong(): Song {
        return Song(
                id = this.id,
                title = this.title,
                artists = this.songArtists.map(SongArtistEntity::toArtist),
                album = this.album.title,
                image = this.album.image,
                duration = this.duration
        )
    }
}
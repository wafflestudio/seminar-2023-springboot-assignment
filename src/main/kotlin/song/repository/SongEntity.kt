package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongsEntity
import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Song
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.hibernate.type.descriptor.sql.internal.BinaryFloatDdlType

@Entity(name = "songs")
class SongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val duration: Long,
    @ManyToOne
    @JoinColumn(name = "album_id")
    val album: AlbumEntity,
    @OneToMany
    @JoinColumn(name = "song_id")
    val songArtists: List<SongArtistsEntity>,
)

package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity
import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository,
) : SongService {

    override fun search(keyword: String): List<Song> =
        songRepository.findAllByTitleContainingWithJoinFetch(keyword)
            .sortedBy { it.title.length }
            .map(::Song)

    override fun searchAlbum(keyword: String): List<Album> =
        albumRepository.findAllByTitleContainingWithJoinFetch(keyword)
            .sortedBy { it.title.length }
            .map(::Album)
}

fun Song(entity: SongEntity): Song = Song(
    id = entity.id,
    title = entity.title,
    artists = entity.artists.map { Artist(it.artist.id, it.artist.name) },
    album = entity.album.title,
    image = entity.album.image,
    duration = run {
        val sec = entity.duration % 60
        "${entity.duration / 60}:${if (sec > 10) "$sec" else "0$sec"}"
    }
)

fun Album(entity: AlbumEntity): Album = Album(
    id = entity.id,
    title = entity.title,
    image = entity.image,
    artist = entity.artist.let { Artist(it.id, it.name) },
)

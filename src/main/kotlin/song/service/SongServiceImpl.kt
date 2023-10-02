package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.*
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository,
) : SongService {

    override fun search(keyword: String): List<Song> {
        return songRepository.findAllByTitleContains(keyword)
            .sortedBy { it.title.count() }
            .map { Song(it) }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        return albumRepository.findAllByTitleContaining(keyword)
            .sortedBy { it.title.count() }
            .map { Album(it) }
    }
}

fun Song(entity: SongEntity): Song {
    return Song(entity.id, entity.title, entity.artists.map { Artist(it.artist) }, entity.album.title, entity.album.image, "${entity.duration}")
}

fun Album(entity: AlbumEntity): Album {
    return Album(entity.id, entity.title, entity.image, Artist(entity.artist))
}

fun Artist(entity: ArtistEntity): Artist = Artist(id = entity.id, entity.name)
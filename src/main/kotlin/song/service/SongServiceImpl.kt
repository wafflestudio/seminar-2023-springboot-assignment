package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository,
) : SongService {

    override fun search(keyword: String): List<Song> =
        songRepository.findByTitleContainingOrderByTitleLengthAsc(keyword).map {
            Song(
                id = it.id,
                title = it.title,
                artists = it.artists.map { songArtist -> Artist(songArtist.artist.id, songArtist.artist.name) },
                album = it.album.title,
                image = it.album.image,
                duration = it.duration.toString(),
            )
        }

    override fun searchAlbum(keyword: String): List<Album> =
        albumRepository.findByTitleContainingOrderByTitleLengthAsc(keyword).map {
            Album(
                id = it.id,
                title = it.title,
                image = it.image,
                artist = Artist(it.artist.id, it.artist.name),
            )
        }
}

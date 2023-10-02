package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository
) : SongService {

    override fun search(keyword: String): List<Song> {
        val songEntities = songRepository.findByTitleContaining(keyword)
        val songs = songEntities.map {
            Song(
                id = it.id,
                title = it.title,
                album = it.album.title,
                image = it.album.image,
                duration = it.duration,
                artists = it.songArtists.map {
                    it2 -> Artist(
                        id = it2.artist.id,
                        name = it2.artist.name
                    )
                }
            )
        }
        return songs.sortedBy { it.title.length }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val albumEntities = albumRepository.findByTitleContaining(keyword)
        val albums = albumEntities.map {
            Album(
                id = it.id,
                title = it.title,
                image = it.image,
                artist = Artist(
                    id = it.artist.id,
                    name = it.artist.name
                ),
            )
        }
        return albums
    }
}

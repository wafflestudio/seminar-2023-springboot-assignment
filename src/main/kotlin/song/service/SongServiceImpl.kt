package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val albumRepository: AlbumRepository,
    private val songRepository: SongRepository
) : SongService {

    override fun search(keyword: String): List<Song> {
        return songRepository.searchSongs(keyword)
            .sortedBy { it.title.length }
            .map {
                Song(
                    id = it.id,
                    title = it.title,
                    album = it.album.title,
                    image = it.album.image,
                    duration = it.duration.toString(),
                    artists = it.songArtists.map {
                        it2 -> Artist(
                            id = it2.artist.id,
                            name = it2.artist.name
                        )
                    }
                )
            }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        return albumRepository.searchAlbums(keyword)
            .sortedBy { it.title.length }
            .map {
                Album(
                    id = it.id,
                    title = it.title,
                    image = it.image,
                    artist = Artist (
                        id = it.artist.id,
                        name = it.artist.name
                        )
                )
            }
    }
}

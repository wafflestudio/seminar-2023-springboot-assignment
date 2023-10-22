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
        return songRepository.searchWithJoinFetch(keyword).map {
                Song(
                    id = it.id,
                    title = it.title,
                    album = it.album.title,
                    image = it.album.image,
                    duration = it.duration.toString(),
                    artists = it.songArtists.map { songArtist ->
                        Artist(
                            id = songArtist.artist.id,
                            name = songArtist.artist.name
                        )
                    }
                )
            }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        return albumRepository.searchWithJoinFetch(keyword)
            .map {
                Album(
                    id = it.id,
                    title = it.title,
                    image = it.image,
                    artist = Artist(
                        id = it.artist.id,
                        name = it.artist.name
                    )
                )
            }
    }
}

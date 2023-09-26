package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository,
) : SongService {

    override fun search(keyword: String): List<Song> {
        val entities = songRepository.findByTitleContainingOrderByTitleAsc(keyword)
        return entities.map { entity ->
            Song(
                id = entity.id,
                title = entity.title,
                artists = entity.song_artists.map { songArtistEntity ->
                    Artist(
                        id = songArtistEntity.artist.id,
                        name = songArtistEntity.artist.name,
                    )
                },
                album = entity.album.title,
                image = entity.album.image,
                duration = entity.duration.toString()
            )
        }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val entities = albumRepository.findByTitleContainingOrderByTitleAsc(keyword)

        return entities.map { entity ->
            Album(
                id = entity.id,
                title = entity.title,
                image = entity.image,
                artist = Artist(id = entity.artist.id, name = entity.artist.name),
            )
        }
    }
}

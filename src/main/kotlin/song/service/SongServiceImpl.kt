package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.SongArtistsEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
        private val songRepository: SongRepository,
) : SongService {

    override fun search(keyword: String): List<Song> {
        val songEntityList = songRepository.findAllByTitle(keyword)

        val songList = songEntityList.map { songEntity ->
            Song(songEntity)
        }

        return songList
    }

    override fun searchAlbum(keyword: String): List<Album> {
        TODO()
    }
}

fun Song(entity: SongEntity) = Song(
        id = entity.id,
        title = entity.title,
        artists = entity.song_artists.map { songArtistsEntity ->
                                          Artist(songArtistsEntity)
        },
        album = entity.album.title,
        image = entity.album.image,
        duration = entity.duration.toString(),
)

fun Artist(entity: SongArtistsEntity) = Artist(
        id = entity.artist.id,
        name = entity.artist.name,
)
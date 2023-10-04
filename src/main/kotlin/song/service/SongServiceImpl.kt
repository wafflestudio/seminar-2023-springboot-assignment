package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.SongArtistEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity
import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository,
) : SongService {

    override fun search(keyword: String): List<Song> {
        val songsContainingKeyword = songRepository.findByTitleContainingWithJoinFetch(keyword)

        return songsContainingKeyword.map { songEntity ->
            val artists = songEntity.song_artists.map { songArtistEntity ->
                Artist(
                    id = songArtistEntity.artist.id,
                    name = songArtistEntity.artist.name,
                )
            }
            Song(
                id = songEntity.id,
                title = songEntity.title,
                artists = artists,
                album = songEntity.album.title,
                image = songEntity.album.image,
                duration = songEntity.duration
            )
        }.sortedBy { it.title.length }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val albumsContainingKeyword = albumRepository.findByTitleContainingWithJoinFetch(keyword)
        return albumsContainingKeyword.map { albumEntity ->
            Album(
                id = albumEntity.id,
                title = albumEntity.title,
                image = albumEntity.image,
                artist = Artist(
                    id = albumEntity.artist.id,
                    name = albumEntity.artist.name
                )
            )
        }.sortedBy { it.title.length }
    }
}

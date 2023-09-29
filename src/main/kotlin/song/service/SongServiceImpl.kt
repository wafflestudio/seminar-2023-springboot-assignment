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
        val result = songRepository.findKeywordSong(keyword).map {
            it -> Song(
            it.id,
            it.title,
            it.song_artists.map {
                it -> Artist(it.artistOfSong.id, it.artistOfSong.name)
            },
            it.album.title,
            it.album.image,
            it.duration.toString())
        }
        return result
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val result = albumRepository.findKeywordAlbum(keyword).map {
            it -> Album(
                it.id, it.title, it.image, Artist(
                    it.artist.id, it.artist.name
                )
            )
        }
        return result
    }
}

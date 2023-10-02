package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.ArtistRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: ArtistRepository,
    private val albumRepository: AlbumRepository
) : SongService {

    override fun search(keyword: String): List<Song> {
        val songs = songRepository.findByTitleContaining(keyword)
        return songs
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val albums = albumRepository.findByTitleContaining(keyword)
        return albums
    }
}

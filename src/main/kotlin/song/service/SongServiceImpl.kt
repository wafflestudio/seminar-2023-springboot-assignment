package com.wafflestudio.seminar.spring2023.song.service

import org.springframework.stereotype.Service
import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository

@Service
class SongServiceImpl (
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository,
): SongService {

    override fun search(keyword: String): List<Song> {
        return songRepository.findByTitleContaining(keyword).map { it.toSong() }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        return albumRepository.findByTitleContaining(keyword).map { it.toAlbum() }
    }
}

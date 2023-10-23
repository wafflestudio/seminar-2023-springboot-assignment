package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.*
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository : SongRepository,
    private val albumRepository: AlbumRepository,
)    : SongService {

    override fun search(keyword: String): List<Song> {
        val songEntityList: List<SongEntity> = songRepository.findAllByTitleContainsOrderByTitle(keyword)
        return songEntityList.map { Song(it) }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val albumEntityList = albumRepository.findAllByTitleContainsKeywordOrderByTitle(keyword)
        return albumEntityList.map { Album((it)) }
    }
}


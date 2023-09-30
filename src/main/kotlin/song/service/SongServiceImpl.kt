package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl (
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository
        ) : SongService {

    override fun search(keyword: String): List<Song> {
        val songEntityList = songRepository.findTitleLike("%$keyword%")
        return songEntityList.map { Song(it) }.sortedBy { it.title.length }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val albumEntityList = albumRepository.findTitleLike("%$keyword%")
        return albumEntityList.map { Album(it) }.sortedBy { it.title.length }
    }
}

package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.*
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
        private val albumRepository : AlbumRepository,
        private val songRepository : SongRepository
) : SongService {

    override fun search(keyword: String): List<Song> {
        return songRepository
                .searchByTitleOrderByTitleLength(keyword)
                .map{it.toSong()}
    }

    override fun searchAlbum(keyword: String): List<Album> {
        return albumRepository
                .searchByTitleOrderByTitleLength(keyword)
                .map{it.toAlbum()}
    }
}

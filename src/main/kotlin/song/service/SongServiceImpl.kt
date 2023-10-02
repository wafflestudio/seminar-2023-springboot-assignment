package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
        val songRepository: SongRepository,
        val albumRepository: AlbumRepository
) : SongService {

    override fun search(keyword: String): List<Song> {
        return songRepository.findAllByTitleKeywordWithJoinFetch(keyword).map { Song(it) }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        return albumRepository.findAllByTitleKeywordWithJoinFetch(keyword).map { Album(it) }
    }
}

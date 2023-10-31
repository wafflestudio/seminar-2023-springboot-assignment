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
        //TODO()
        val songEntities = songRepository.findByTitleLike("%$keyword%")
        return songEntities.map { it.toSong() }.sortedBy { it.title.length }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        //TODO()
        val albumEntities = albumRepository.findByTitleLike("%$keyword%")
        return albumEntities.map { it.toAlbum() }.sortedBy { it.title.length }
    }
}

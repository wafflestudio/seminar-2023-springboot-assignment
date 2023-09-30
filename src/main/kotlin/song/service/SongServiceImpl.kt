package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository
) : SongService {

    override fun search(keyword: String): List<Song> {
        val songEntities = songRepository.findByTitleContainingWithJoinFetch(keyword)
        return songEntities.map { entity -> SongMapper.toSongDTO(entity) }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val albumEntities = albumRepository.findByTitleContainingWithJoinFetch(keyword)
        return albumEntities.map { entity -> SongMapper.toAlbumDTO(entity) }
    }
}
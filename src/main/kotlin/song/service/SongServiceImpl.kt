package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity
import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository,
) : SongService {

    override fun search(keyword: String): List<Song> {
        return songRepository.searchWithJoinFetch("%$keyword%").map(SongEntity::toSong)
    }

    override fun searchAlbum(keyword: String): List<Album> {
        return albumRepository.searchWithJoinFetch("%$keyword%").map(AlbumEntity::toAlbum)
    }
   
}

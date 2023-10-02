package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.EntityMapper.mapAlbumEntitiesToAlbums
import com.wafflestudio.seminar.spring2023.song.repository.SongEntityMapper.mapSongEntitiesToSongs
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
        private val songRepository: SongRepository,
        private val albumRepository: AlbumRepository
) : SongService {

    override fun search(keyword: String): List<Song> {
        val songEntity = songRepository.searchSongsWithFetchJoin(keyword)
        return mapSongEntitiesToSongs(songEntity)
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val albumEntity = songRepository.searchAlbumsWithFetchJoin(keyword)
        return mapAlbumEntitiesToAlbums(albumEntity)
    }
}

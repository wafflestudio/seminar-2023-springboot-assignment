package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.service.AlbumEntityMapper.mapAlbumEntitiesToAlbums
import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.SongEntityMapper.mapSongEntitiesToSongs
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository
) : SongService {

    override fun search(keyword: String): List<Song> {
        val songEntity = songRepository.searchSongTWithFetchJoin(keyword)
        return mapSongEntitiesToSongs(songEntity)
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val albumEntity = albumRepository.searchSongsTWithFetchJoin(keyword)
        return mapAlbumEntitiesToAlbums(albumEntity)
    }
}

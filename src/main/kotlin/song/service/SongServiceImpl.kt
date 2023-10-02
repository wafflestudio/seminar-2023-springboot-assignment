package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity
//import com.wafflestudio.seminar.spring2023.song.service.AlbumEntityMapper.mapAlbumEntitiesToAlbums
import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
//import com.wafflestudio.seminar.spring2023.song.service.SongEntityMapper.mapSongEntitiesToSongs
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository
) : SongService {

    override fun search(keyword: String): List<Song> {
        return songRepository.searchSongTWithFetchJoin("%$keyword%").map(SongEntity::toSong)
        //val songEntities = songRepository.searchSongTWithFetchJoin(keyword)
        //return songEntities.map { entity -> SongMapper.toSongDTO(entity) }
        //val songEntity = songRepository.searchSongTWithFetchJoin("%:keyword%")
        //return mapSongEntitiesToSongs(songEntity)
    }

    override fun searchAlbum(keyword: String): List<Album> {
        return albumRepository.searchSongsTWithFetchJoin("%$keyword%").map(AlbumEntity::toAlbum)
        //val albumEntity = albumRepository.searchSongsTWithFetchJoin("%:keyword%")
        //return mapAlbumEntitiesToAlbums(albumEntity)
       // val albumEntities = albumRepository.searchSongsTWithFetchJoin(keyword)
        //return albumEntities.map { entity -> SongMapper.toAlbumDTO(entity) }
    }
}

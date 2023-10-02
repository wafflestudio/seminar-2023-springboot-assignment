package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongArtistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
        private val songRepository: SongRepository,
        private val albumRepository: AlbumRepository
) : SongService {

    override fun search(keyword: String): List<Song> {
        //TODO()
        val songEntityList = songRepository.findSongByTitleContainsWithJoinFetch(keyword).sortedBy { it.title.length }
        return songEntityList.map { Song(
                id = it.id,
                title = it.title,
                artists = it.songArtist.map { Artist(it.artist.id, it.artist.name) },
                album = it.album.title,
                image = it.album.image,
                duration = it.duration.toString()
        ) }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        //TODO()
        val albumEntityList = albumRepository.findAlbumEntityByTitleContains(keyword).sortedBy { it.title.length }
        return albumEntityList.map { Album(
                id = it.id,
                title = it.title,
                image = it.image,
                artist = Artist(it.artist.id, it.artist.name)
        ) }
    }
}

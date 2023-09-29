package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity
import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository
    ) : SongService {
    override fun search(keyword: String): List<Song> {
        val songEntities = songRepository.findSongsByTitleContaining(keyword)
        val songList = songEntities.map { mapSongEntityToSong(it) }
        return songList.sortedBy { song -> song.title.length }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val albumEntities = albumRepository.findAlbumsByTitleContaining(keyword)
        val albumList = albumEntities.map { mapAlbumEntityToAlbum(it)  }
        return albumList.sortedBy { album -> album.title.length }
    }

    private fun mapSongEntityToSong(entity: SongEntity): Song {
        val artistList = entity.artists.map { artistEntity ->
            Artist(artistEntity.id, artistEntity.name)
        }

        return Song(
            id = entity.id,
            title = entity.title,
            artists = artistList,
            album = entity.album.title,
            image = entity.album.image,
            duration = entity.duration
        )
    }

    private fun mapAlbumEntityToAlbum(entity: AlbumEntity): Album {
        val artist = Artist(entity.artist.id, entity.artist.name)

        return Album(
            entity.id,
            entity.title,
            entity.image,
            artist,
        )
    }
}

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
        return songRepository.findByTitleContaining(keyword).map {
            songEntity -> Song(
            id = songEntity.id,
            title = songEntity.title,
            artists = songEntity.song_artists.map{
                songArtistEntity ->  Artist(id = songArtistEntity.artist.id, name = songArtistEntity.artist.name)
            },
            album = songEntity.album.title,
            image = songEntity.album.image,
            duration = songEntity.duration) }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        return albumRepository.findByTitleContaining(title = keyword).map {
            albumEntity -> Album(
            id = albumEntity.id,
            title = albumEntity.title,
            image = albumEntity.image,
            artist = Artist(id = albumEntity.artist.id, name = albumEntity.artist.name))
        }
    }
}

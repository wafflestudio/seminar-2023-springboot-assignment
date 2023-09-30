package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SongServiceImpl @Autowired constructor(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository,
    private val songRepository: SongRepository,
) : SongService {

    override fun search(keyword: String): List<Song> {

        val songs = mutableListOf<Song>()

        val songsMatchingSongName = songRepository.findByTitleContaining(keyword)
        songs.addAll(songsMatchingSongName.toSongList())

        return songs.sortedBy { it.title.length }

    }

    override fun searchAlbum(keyword: String): List<Album> {
        val albums = mutableListOf<Album>()
        val albumsMatchingAlbumName: List<AlbumEntity> = albumRepository.findByTitleContaining(keyword)
        albumsMatchingAlbumName.forEach { albumEntity ->
            val album = Album(
                id = albumEntity.id,
                title = albumEntity.title,
                image = albumEntity.image,
                artist = Artist(
                    id = albumEntity.artist.id,
                    name = albumEntity.artist.name,
                )
            )
            albums.add(album)
        }
        return albums.sortedBy { it.title.length }
    }
}

private fun List<SongEntity>.toSongList(): List<Song> {
    return this.map { songEntity ->
        Song(
            id = songEntity.id,
            title = songEntity.title,
            artists = songEntity.artists.map { artistEntity ->
                Artist(
                    id = artistEntity.id,
                    name = artistEntity.name
                )
            },
            album = songEntity.album.title,
            image = songEntity.image,
            duration = songEntity.duration
        )
    }


}

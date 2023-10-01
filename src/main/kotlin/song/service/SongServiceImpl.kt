package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.*
import org.springframework.stereotype.Service

@Service
class SongServiceImpl(
        private val songRepository: SongRepository,
        private val albumRepository: AlbumRepository,
) : SongService {

    override fun search(keyword: String): List<Song> {
        val songEntityList = songRepository.findAllSongsByTitleWithJoinFetch(keyword)

        return songEntityList.map { songEntity ->
            toSong(songEntity)
        }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val albumEntityList = albumRepository.findAllAlbumsByTitleWithJoinFetch(keyword)

        return albumEntityList.map { albumEntity ->
            toAlbum(albumEntity)
        }
    }
}

fun toSong(entity: SongEntity) = Song(
        id = entity.id,
        title = entity.title,
        artists = entity.song_artists.map { songArtistsEntity ->
                                          toArtistFromSong(songArtistsEntity)
        },
        album = entity.album.title,
        image = entity.album.image,
        duration = entity.duration.toString(),
)

fun toArtistFromSong(entity: SongArtistsEntity) = Artist(
        id = entity.artist.id,
        name = entity.artist.name,
)

fun toAlbum(entity: AlbumEntity) = Album(
        id = entity.id,
        title = entity.title,
        image = entity.image,
        artist = toArtistFromArtistEntity(entity.artist)
)

fun toArtistFromArtistEntity(entity: ArtistEntity) = Artist(
        id = entity.id,
        name = entity.name,
)
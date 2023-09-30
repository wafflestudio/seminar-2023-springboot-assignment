package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.*
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service
@Service
class SongServiceImpl(private val songArtistsRepository: SongArtistsRepository, private val songRepository: SongRepository, private val albumRepository: AlbumRepository, private val artistRepository: ArtistRepository) : SongService {

    override fun search(keyword: String): List<Song> {
        val searchRes = songRepository.findByTitleContaining(keyword = keyword)
        return searchRes.map { songartistinfo ->
            Song(
                    id = songartistinfo.id,
                    title = songartistinfo.title,
                    duration = songartistinfo.duration,
                    album = toAlbum(songartistinfo.album),
                    artists = songartistinfo.songArtists.map { it -> Artist(id = it.artist.id, name = it.artist.name) },
                    image = songartistinfo.album.image,
            )
        }.sortedBy { it.title.length }
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val searchRes = albumRepository.findAlbumEntityByTitleContaining(keyword = keyword)
        return searchRes!!.map{ albuminfo ->
            toAlbum(albuminfo)
        }.sortedBy { it.title.length }
    }
    fun toAlbum(albuminfo : AlbumEntity) : Album =
        Album(
                id = albuminfo.id,
                title = albuminfo.title,
                image = albuminfo.image,
                artist = Artist(albuminfo.artist.id, albuminfo.artist.name))
    fun toArtist(artistinfo : ArtistEntity) : Artist =
            Artist(
                    id = artistinfo.id,
                    name = artistinfo.name,)
}

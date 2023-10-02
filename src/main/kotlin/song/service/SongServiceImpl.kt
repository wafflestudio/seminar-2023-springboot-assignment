package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.*
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service
@Service
class SongServiceImpl(private val songRepository: SongRepository, private val albumRepository: AlbumRepository) : SongService {

    override fun search(keyword: String): List<Song> {
        val searchRes = songRepository.findByTitleContaining(keyword = keyword)
        return searchRes.map { toSong(it) }.sortedBy { it.title.length }
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

    override fun toSong(songEntity: SongEntity) : Song =
            Song(
                    id = songEntity.id,
                    image = songEntity.album.image,
                    duration = songEntity.duration,
                    title = songEntity.title,
                    album = toAlbum(songEntity.album),
                    artists = songEntity.songArtists.map{ toArtist(it.artist) }
            )
}

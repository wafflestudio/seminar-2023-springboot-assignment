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
        val songEntities = songRepository.findByTitleContainingWithArtists(keyword=keyword)
        //PlaylistServiceImpl 코드 재사용 시간나면 더 느슨하게
        return songEntities.map { sEntity->
            Song(id = sEntity.id,
                title = sEntity.title,
                artists = sEntity.songArtists.map { saEntity->
                    val aArtist = saEntity.artist
                    Artist(id= aArtist.id,
                        name = aArtist.name)
                }.toList(),
                album = sEntity.album.title,
                image = sEntity.album.image,
                duration = sEntity.duration.toString()
            )}.toList()
    }

    override fun searchAlbum(keyword: String): List<Album> {
        val albumEntities = albumRepository.findByTitleContainingWithArtist(keyword = keyword)
        return albumEntities.map { aEntity ->
            Album(id = aEntity.id,
                title = aEntity.title,
                image = aEntity.image,
                artist = Artist(id = aEntity.artist.id,
                    name = aEntity.artist.name)
                )
        }
    }
}

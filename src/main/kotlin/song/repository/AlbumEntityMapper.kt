package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity
import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Artist

object EntityMapper {

    fun mapAlbumEntitiesToAlbums(albumEntities: List<AlbumEntity>): List<Album> {
        return albumEntities.map { albumEntity ->
            Album(
                    id = albumEntity.id,
                    title = albumEntity.title,
                    image = albumEntity.image,
                    artist = Artist(
                            id = albumEntity.artist.id,
                            name = albumEntity.artist.name
                    )
            )
        }
    }
}

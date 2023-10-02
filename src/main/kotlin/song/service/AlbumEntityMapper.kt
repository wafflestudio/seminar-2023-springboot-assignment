//망한mapper
/*
package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity

object AlbumEntityMapper {

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
*/
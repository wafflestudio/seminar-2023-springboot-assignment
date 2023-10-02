//망한mapper
/*
package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity

object SongEntityMapper {

    fun mapSongEntitiesToSongs(songEntities: List<SongEntity>): List<Song> {
        return songEntities.map { songEntity ->
            Song(
                id = songEntity.id,
                title = songEntity.title,
                artists = songEntity.artists.map { songArtistEntity ->
                    Artist(
                        id = songArtistEntity.id,
                        name = songArtistEntity.artist.name
                    )
                },
                album = songEntity.album.title, // Use the album title from the related entity
                image = songEntity.image,
                duration = songEntity.duration.toString() // Assuming duration is an Int
            )
        }
    }
}
*/
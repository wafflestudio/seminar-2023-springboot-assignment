package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.service.Song
import com.wafflestudio.seminar.spring2023.song.service.Artist

object SongEntityMapper {

    fun mapSongEntitiesToSongs(songEntities: List<SongEntity>): List<Song> {
        return songEntities.map { songEntity ->
            Song(
                    id = songEntity.id,
                    title = songEntity.title,
                    artists = songEntity.artists.map { artistEntity ->
                        Artist(
                                id = artistEntity.id,
                                name = artistEntity.name
                        )
                    },
                    album = songEntity.album.title, // Use the album title from the related entity
                    image = songEntity.image,
                    duration = songEntity.duration.toString() // Assuming duration is an Int
            )
        }
    }
}

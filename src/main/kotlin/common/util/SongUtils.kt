package com.wafflestudio.seminar.spring2023.common.util

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song

class SongUtils {
    companion object {
        fun mapSongEntityToSong(entity: SongEntity): Song {
            val artistList = entity.artists.map { artistEntity ->
                Artist(artistEntity.id, artistEntity.name)
            }

            return Song(
                id = entity.id,
                title = entity.title,
                artists = artistList,
                album = entity.album.title,
                image = entity.album.image,
                duration = entity.duration
            )
        }
    }
}
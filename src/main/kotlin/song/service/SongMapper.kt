/*package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity
import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity

class SongMapper {
    companion object {
        fun toSongDTO(entity: SongEntity): Song {
            return Song(
                id = entity.id,
                title = entity.title,
                artists = entity.songArtists.map { songArtist ->
                    toArtistDTO(songArtist.artist)
                },
                album = entity.album.title,
                image = entity.album.image,
                duration = entity.duration.toString()
            )
        }

        fun toAlbumDTO(entity: AlbumEntity): Album {
            return Album(
                id = entity.id,
                title = entity.title,
                image = entity.image,
                artist = toArtistDTO(entity.artist)
            )
        }

        private fun toArtistDTO(entity: ArtistEntity): Artist {
            return Artist(
                id = entity.id,
                name = entity.name
            )
        }
    }
}*/
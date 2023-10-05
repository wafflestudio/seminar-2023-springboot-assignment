package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongArtistsEntity

data class Artist(
    val id: Long,
    val name: String,
)

fun Artist(songArtistsEntity: SongArtistsEntity) =Artist(
        id = songArtistsEntity.artist.id,
        name = songArtistsEntity.artist.name,
)

fun Artist(artistEntity: ArtistEntity) = Artist(
    id = artistEntity.id,
    name = artistEntity.name,
)
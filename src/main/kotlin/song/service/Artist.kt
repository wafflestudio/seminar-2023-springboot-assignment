package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity

data class Artist(
    val id: Long,
    val name: String,
)

fun Artist(artistEntity: ArtistEntity) = Artist(
    id = artistEntity.id,
    name = artistEntity.name
)

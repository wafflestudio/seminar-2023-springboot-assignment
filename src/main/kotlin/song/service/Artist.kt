package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity

data class Artist(
    val id: Long,
    val name: String,
) {
    constructor(entity: ArtistEntity): this(
        id = entity.id,
        name = entity.name,
    )
}

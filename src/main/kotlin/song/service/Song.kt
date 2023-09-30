package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity

data class Song(
    val id: Long,
    val title: String,
    val artists: List<Artist>,
    val album: String,
    val image: String,
    val duration: Int,
)

fun Song(songEntity: SongEntity) = Song (
    id = songEntity.id,
    title = songEntity.title,
    artists = songEntity.songArtistRelationshipList
        .map { relationshipEntity -> Artist(relationshipEntity.artist) }
        .sortedBy { it.id },
    album = songEntity.album.title,
    image = songEntity.album.image,
    duration = songEntity.duration
)


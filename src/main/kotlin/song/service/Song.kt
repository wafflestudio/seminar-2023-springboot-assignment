package com.wafflestudio.seminar.spring2023.song.service

data class Song(
    val id: Long,
    val title: String,
    val artists: List<Artist>,
    val album: Album,
    val image: String,
    val duration: Long,
)

package com.wafflestudio.seminar.spring2023.song.service

data class Album(
    val id: Long,
    val title: String,
    val image: String,
    val artist: Artist,
)

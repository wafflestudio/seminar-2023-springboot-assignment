package com.wafflestudio.seminar.spring2023.song.service

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity

interface SongService {
    fun search(keyword: String): List<Song>
    fun searchAlbum(keyword: String): List<Album>

    fun toSong(songEntity: SongEntity) : Song
}

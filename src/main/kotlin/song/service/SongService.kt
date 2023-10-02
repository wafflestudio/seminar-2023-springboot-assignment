package com.wafflestudio.seminar.spring2023.song.service


interface SongService {
    fun search(keyword: String): List<Song>
    fun searchAlbum(keyword: String): List<Album>
}

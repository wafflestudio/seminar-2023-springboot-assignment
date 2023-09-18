package com.wafflestudio.seminar.spring2023.song.service

import org.springframework.stereotype.Service

@Service
class SongServiceImpl : SongService {

    override fun search(keyword: String): List<Song> {
        TODO()
    }

    override fun searchAlbum(keyword: String): List<Album> {
        TODO()
    }
}

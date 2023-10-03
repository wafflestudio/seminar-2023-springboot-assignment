package com.wafflestudio.seminar.spring2023.song.controller

import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Song
import com.wafflestudio.seminar.spring2023.song.service.SongService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SongController {

    @GetMapping("/api/v1/songs")
    fun searchSong( //노래 검색
        @RequestParam keyword: String,
    ): SearchSongResponse {
        TODO()
    }

    @GetMapping("/api/v1/albums")
    fun searchAlbum( //앨범 검색
        @RequestParam keyword: String,
    ): SearchAlbumResponse {
        TODO()
    }
}

data class SearchSongResponse(
    val songs: List<Song>,
)

data class SearchAlbumResponse(
    val albums: List<Album>,
)

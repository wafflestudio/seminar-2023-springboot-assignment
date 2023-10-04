package com.wafflestudio.seminar.spring2023.song.controller

import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Song
import com.wafflestudio.seminar.spring2023.song.service.SongService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SongController(
    private val songService: SongService,
) {

    @GetMapping("/api/v1/songs")
    fun searchSong(
        @RequestParam keyword: String,
    ): SearchSongResponse {
        val songs = songService.search(keyword)
        return SearchSongResponse(songs)
    }

    @GetMapping("/api/v1/albums")
    fun searchAlbum(
        @RequestParam keyword: String,
    ): SearchAlbumResponse {
        val albums = songService.searchAlbum(keyword)
        return SearchAlbumResponse(albums)
    }
}

data class SearchSongResponse(
    val songs: List<Song>,
)

data class SearchAlbumResponse(
    val albums: List<Album>,
)

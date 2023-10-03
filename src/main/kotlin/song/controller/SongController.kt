package com.wafflestudio.seminar.spring2023.song.controller

import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Song
import com.wafflestudio.seminar.spring2023.song.service.SongService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SongController(
        private val songService: SongService, //써야할 서비스가 songservice 밖에 없으므로 이것만 주입 받아오면 된다
) {

    @GetMapping("/api/v1/songs")
    fun searchSong( //노래 검색
        @RequestParam keyword: String,//키워드가 포함된 애들을 검색해야하므로 요청해서 키워드를 받아와야 한다(annotation으로도 명시)
    ): SearchSongResponse {
        songService.search(keyword)
    }

    @GetMapping("/api/v1/albums")
    fun searchAlbum( //앨범 검색
        @RequestParam keyword: String,
    ): SearchAlbumResponse {
        songService.searchAlbum(keyword)
    }
}

data class SearchSongResponse(
    val songs: List<Song>,
)

data class SearchAlbumResponse(
    val albums: List<Album>,
)

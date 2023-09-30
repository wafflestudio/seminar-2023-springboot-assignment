package com.wafflestudio.seminar.spring2023.song.controller

import com.wafflestudio.seminar.spring2023.song.service.Album
import com.wafflestudio.seminar.spring2023.song.service.Song
import com.wafflestudio.seminar.spring2023.song.service.SongService
import com.wafflestudio.seminar.spring2023.user.service.SignUpBadPasswordException
import com.wafflestudio.seminar.spring2023.user.service.SignUpBadUsernameException
import com.wafflestudio.seminar.spring2023.user.service.SignUpUsernameConflictException
import com.wafflestudio.seminar.spring2023.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SongController(
    private val songService: SongService,
)  {
    //exception 처리?

    @GetMapping("/api/v1/songs")
    fun searchSong(
        @RequestParam keyword: String,
    ): ResponseEntity<SearchSongResponse> {
        val songs = songService.search(keyword)
        if (songs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SearchSongResponse(emptyList()))
        }
        return ResponseEntity.ok(SearchSongResponse(songs))
    }

    @GetMapping("/api/v1/albums")
    fun searchAlbum(
        @RequestParam keyword: String,
    ): ResponseEntity<SearchAlbumResponse> {
        val albums = songService.searchAlbum(keyword)
        if (albums.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SearchAlbumResponse(emptyList()))
        }
        return ResponseEntity.ok(SearchAlbumResponse(albums))
    }
}

data class SearchSongResponse(
    val songs: List<Song>,
)

data class SearchAlbumResponse(
    val albums: List<Album>,
)

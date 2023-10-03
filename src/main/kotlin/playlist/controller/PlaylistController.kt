package com.wafflestudio.seminar.spring2023.playlist.controller

import com.wafflestudio.seminar.spring2023.playlist.service.Playlist
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistException
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistGroup
import com.wafflestudio.seminar.spring2023.user.service.Authenticated
import com.wafflestudio.seminar.spring2023.user.service.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaylistController {

    @GetMapping("/api/v1/playlist-groups")
    fun getPlaylistGroup(): PlaylistGroupsResponse { //플레이리스트 그룹 조회
        TODO()
    }

    @GetMapping("/api/v1/playlists/{id}")
    fun getPlaylist( //플레이리스트 조회
        @PathVariable id: Long,
        user: User?,
    ): PlaylistResponse {
        TODO()
    }

    @PostMapping("/api/v1/playlists/{id}/likes")
    fun likePlaylist( //플레이 리스트 좋아요
        @PathVariable id: Long,
        @Authenticated user: User,
    ) {
        TODO()
    }

    @DeleteMapping("/api/v1/playlists/{id}/likes")
    fun undoLikePlaylist( //플레이 리스트 좋아요 취소
        @PathVariable id: Long,
        @Authenticated user: User,
    ) {
        TODO()
    }

    @ExceptionHandler
    fun handleException(e: PlaylistException): ResponseEntity<Unit> {
        TODO()
    }
}

data class PlaylistGroupsResponse(val groups: List<PlaylistGroup>)

data class PlaylistResponse(val playlist: Playlist, val isLiked: Boolean)

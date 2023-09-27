package com.wafflestudio.seminar.spring2023.playlist.controller

import com.wafflestudio.seminar.spring2023.playlist.service.*
import com.wafflestudio.seminar.spring2023.user.service.Authenticated
import com.wafflestudio.seminar.spring2023.user.service.User
import com.wafflestudio.seminar.spring2023.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaylistController(
    private val playlistService: PlaylistService,
    private val playlistLikeService: PlaylistLikeService,
    private val userService: UserService,
) {

    @GetMapping("/api/v1/playlist-groups")
    fun getPlaylistGroup(): PlaylistGroupsResponse {
        val groups = playlistService.getGroups()

        return PlaylistGroupsResponse(groups)
    }

    @GetMapping("/api/v1/playlists/{id}")
    fun getPlaylist(
        @PathVariable id: Long,
        user: User?,
    ): PlaylistResponse {
        val playlist = playlistService.get(id)
        val isLiked = user?.let { playlistLikeService.exists(id, it.id) } ?: false
        return PlaylistResponse(playlist, isLiked)
    }

    @PostMapping("/api/v1/playlists/{id}/likes")
    fun likePlaylist(
        @PathVariable id: Long,
        @Authenticated user: User,
    ) {
        playlistLikeService.create(id, user.id)
    }

    @DeleteMapping("/api/v1/playlists/{id}/likes")
    fun undoLikePlaylist(
        @PathVariable id: Long,
        @Authenticated user: User,
    ) {
        playlistLikeService.delete(id, user.id)
    }

    @ExceptionHandler
    fun handleException(e: PlaylistException): ResponseEntity<Unit> {
        val status = when (e) {
            is PlaylistNotFoundException -> 404
            is PlaylistAlreadyLikedException, is PlaylistNeverLikedException -> 409
        }
        return ResponseEntity.status(status).build()
    }
}

data class PlaylistGroupsResponse(val groups: List<PlaylistGroup>)

data class PlaylistResponse(val playlist: Playlist, val isLiked: Boolean)

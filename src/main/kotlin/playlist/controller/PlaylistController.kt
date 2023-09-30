package com.wafflestudio.seminar.spring2023.playlist.controller

import com.wafflestudio.seminar.spring2023.playlist.service.*
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
class PlaylistController(
    private val playlistService : PlaylistService,
    private val playlistLikeService : PlaylistLikeService
) {
    @GetMapping("/api/v1/playlist-groups")
    fun getPlaylistGroup(): PlaylistGroupsResponse {
        return PlaylistGroupsResponse(groups = playlistService.getGroups())
    }

    @GetMapping("/api/v1/playlists/{id}")
    fun getPlaylist(
        @PathVariable id: Long,
        user: User?,
    ): PlaylistResponse {
        val isLiked : Boolean = if (user != null) playlistLikeService.exists(userId = user.id, playlistId = id) else false
        return PlaylistResponse(playlist = playlistService.get(id), isLiked = isLiked)
    }

    @PostMapping("/api/v1/playlists/{id}/likes")
    fun likePlaylist(
        @PathVariable id: Long,
        @Authenticated user: User,
    ) {
        playlistLikeService.create(playlistId = id, userId = user.id)
    }

    @DeleteMapping("/api/v1/playlists/{id}/likes")
    fun undoLikePlaylist(
        @PathVariable id: Long,
        @Authenticated user: User,
    ) {
        playlistLikeService.delete(playlistId = id, userId = user.id)
    }

    @ExceptionHandler
    fun handleException(e: PlaylistException): ResponseEntity<Unit> {
        val statusCode = when (e) {
            is PlaylistNotFoundException -> 404
            is PlaylistAlreadyLikedException, is PlaylistNeverLikedException -> 409
        }
        return ResponseEntity.status(statusCode).build()
    }
}

data class PlaylistGroupsResponse(val groups: List<PlaylistGroup>)

data class PlaylistResponse(val playlist: Playlist, val isLiked: Boolean)

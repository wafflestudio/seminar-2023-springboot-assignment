package com.wafflestudio.seminar.spring2023.playlist.controller

import com.wafflestudio.seminar.spring2023.playlist.service.*
import com.wafflestudio.seminar.spring2023.user.service.Authenticated
import com.wafflestudio.seminar.spring2023.user.service.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaylistController (
    private val playlistService: PlaylistService,
    private val playlistLikeService: PlaylistLikeService
) {
    @GetMapping("/api/v1/playlist-groups")
    fun getPlaylistGroup(): PlaylistGroupsResponse {
        val playlistGroup = playlistService.getGroups()
        return PlaylistGroupsResponse(groups = playlistGroup)
    }

    @GetMapping("/api/v1/playlists/{id}")
    fun getPlaylist(
        @PathVariable id: Long,
        user: User?,
    ): PlaylistResponse {
        val playlist = playlistService.get(id = id)
        val isLiked = getUserLikeStatus(playlistId = id, user = user)
        return PlaylistResponse(playlist = playlist, isLiked = isLiked)
    }

    private fun getUserLikeStatus(playlistId: Long, user: User?): Boolean {
        return user?.let { playlistLikeService.exists(playlistId = playlistId, userId = it.id) } ?: false
    }

    @PostMapping("/api/v1/playlists/{id}/likes")
    fun likePlaylist(
        @PathVariable id: Long,
        @Authenticated user: User,
    ) : ResponseEntity<Unit> {
        playlistLikeService.create(playlistId = id, userId = user.id)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/api/v1/playlists/{id}/likes")
    fun undoLikePlaylist(
        @PathVariable id: Long,
        @Authenticated user: User,
    ) : ResponseEntity<Unit> {
        playlistLikeService.delete(playlistId = id, userId = user.id)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @ExceptionHandler
    fun handleException(e: PlaylistException): ResponseEntity<Unit> {
        val status = when (e) {
            is PlaylistNotFoundException -> HttpStatus.NOT_FOUND
            is PlaylistAlreadyLikedException -> HttpStatus.BAD_REQUEST
            is PlaylistNeverLikedException -> HttpStatus.BAD_REQUEST
            is UserNotFoundException -> HttpStatus.BAD_REQUEST
        }
        return ResponseEntity.status(status).build()
    }
}

data class PlaylistGroupsResponse(val groups: List<PlaylistGroup>)

data class PlaylistResponse(val playlist: Playlist, val isLiked: Boolean)

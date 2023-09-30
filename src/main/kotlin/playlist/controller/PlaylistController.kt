package com.wafflestudio.seminar.spring2023.playlist.controller

import com.wafflestudio.seminar.spring2023.playlist.service.*
import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaylistController(
        private val playListService: PlaylistService,
        private val playlistLikeService: PlaylistLikeService
) {

    @GetMapping("/api/v1/playlist-groups")
    fun getPlaylistGroup(): PlaylistGroupsResponse {
        return PlaylistGroupsResponse(playListService.getGroups())
    }

    @GetMapping("/api/v1/playlists/{id}")
    fun getPlaylist(
        @PathVariable id: Long,
        user: User?,
    ): PlaylistResponse {
        val playlist = playListService.get(id)
        val liked = if (user==null) {
            false
        } else {
            playlistLikeService.exists(playlistId = id, userId = user.id)
        }
        return PlaylistResponse(playlist, liked)
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
    fun handlePlaylistException(e: PlaylistException): ResponseEntity<Unit> {
        val status = when (e) {
            is PlaylistNotFoundException -> HttpStatus.NOT_FOUND
            is PlaylistAlreadyLikedException, is PlaylistNeverLikedException -> HttpStatus.CONFLICT
        }
        return ResponseEntity.status(status).build()
    }

    @ExceptionHandler
    fun handleUserException(e: UserException): ResponseEntity<Unit> {
        val status = when (e) {
            is SignUpBadUsernameException, is SignUpBadPasswordException, is SignInInvalidPasswordException
            -> HttpStatus.BAD_REQUEST
            is AuthenticateException -> HttpStatus.UNAUTHORIZED
            is SignInUserNotFoundException -> HttpStatus.NOT_FOUND
            is SignUpUsernameConflictException -> HttpStatus.CONFLICT
        }
        return ResponseEntity.status(status).build()
    }
}

data class PlaylistGroupsResponse(val groups: List<PlaylistGroup>)

data class PlaylistResponse(val playlist: Playlist, val isLiked: Boolean)

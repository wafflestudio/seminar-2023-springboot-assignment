package com.wafflestudio.seminar.spring2023.playlist.controller

import com.wafflestudio.seminar.spring2023.playlist.service.*
import com.wafflestudio.seminar.spring2023.user.service.Authenticated
import com.wafflestudio.seminar.spring2023.user.service.User
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
        private val playlistService: PlaylistService,
        private val playlistLikeService: PlaylistLikeService
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
        val playlist = playlistService.get(id)

        var isLiked = false
        user?.let {
            isLiked = playlistLikeService.exists(id, user.id)
        }

        return PlaylistResponse(playlist, isLiked)
    }

    @PostMapping("/api/v1/playlists/{id}/likes")
    fun likePlaylist(
        @PathVariable id: Long,
        @Authenticated user: User,
    ) : ResponseEntity<Unit>{
        if (playlistLikeService.exists(playlistId = id, userId = user.id)) {
            throw PlaylistAlreadyLikedException()
        } else {
            playlistLikeService.create(playlistId = id, userId = user.id)
            return ResponseEntity.status(200).build()
        }
    }

    @DeleteMapping("/api/v1/playlists/{id}/likes")
    fun undoLikePlaylist(
        @PathVariable id: Long,
        @Authenticated user: User,
    ) : ResponseEntity<Unit>{
        if (playlistLikeService.exists(playlistId = id, userId = user.id)) {
            throw PlaylistNeverLikedException()
        } else {
            playlistLikeService.delete(playlistId = id, userId = user.id)
            return ResponseEntity.status(204).build()
        }
    }

    @ExceptionHandler
    fun handleException(e: PlaylistException): ResponseEntity<Unit> {
        return when (e) {
            is PlaylistAlreadyLikedException, is PlaylistNeverLikedException -> ResponseEntity.status(409).build()
            is PlaylistNotFoundException -> ResponseEntity.status(404).build()
        }
    }
}

data class PlaylistGroupsResponse(val groups: List<PlaylistGroup>)

data class PlaylistResponse(val playlist: Playlist, val isLiked: Boolean)

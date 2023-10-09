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
    private val playlistService: PlaylistService,
    private val playlistLikeService: PlaylistLikeService
) {

    @GetMapping("/api/v1/playlist-groups")
    fun getPlaylistGroup(): PlaylistGroupsResponse = PlaylistGroupsResponse(groups = playlistService.getGroups())

    @GetMapping("/api/v1/playlists/{id}")
    fun getPlaylist(
        @PathVariable id: Long,
        user: User?,
    ): PlaylistResponse {
        val playlist = playlistService.get(id)
        return PlaylistResponse(
            playlist = playlist,
            isLiked = user?.let { playlistLikeService.exists(playlist.id, it.id) } ?: false
        )
    }

    @PostMapping("/api/v1/playlists/{id}/likes")
    fun likePlaylist(
        @PathVariable id: Long,
        @Authenticated user: User,
    ) {
        val playlist = playlistService.get(id)
        playlistLikeService.create(playlist.id, user.id)
    }

    @DeleteMapping("/api/v1/playlists/{id}/likes")
    fun undoLikePlaylist(
        @PathVariable id: Long,
        @Authenticated user: User,
    ) {
        val playlist = playlistService.get(id)
        playlistLikeService.delete(playlist.id, user.id)
    }

    @ExceptionHandler
    fun handleException(e: PlaylistException): ResponseEntity<Unit> = when (e) {
        is PlaylistNotFoundException -> ResponseEntity.notFound().build()
        is PlaylistAlreadyLikedException -> ResponseEntity.badRequest().build()
        is PlaylistNeverLikedException -> ResponseEntity.badRequest().build()
    }
}

data class PlaylistGroupsResponse(val groups: List<PlaylistGroup>)

data class PlaylistResponse(val playlist: Playlist, val isLiked: Boolean)

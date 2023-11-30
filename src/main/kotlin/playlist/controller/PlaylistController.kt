package com.wafflestudio.seminar.spring2023.playlist.controller

import com.wafflestudio.seminar.spring2023.playlist.service.Playlist
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistAlreadyLikedException
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistException
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistGroup
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistLikeService
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistNeverLikedException
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistNotFoundException
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistService
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistViewService
import com.wafflestudio.seminar.spring2023.playlist.service.SortPlaylist.Type
import com.wafflestudio.seminar.spring2023.user.service.Authenticated
import com.wafflestudio.seminar.spring2023.user.service.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Executors

@RestController
class PlaylistController(
    private val playlistService: PlaylistService,
    private val playlistLikeService: PlaylistLikeService,
    private val playlistViewService: PlaylistViewService,
) {
    private val threads = Executors.newFixedThreadPool(4)

    @GetMapping("/api/v1/playlist-groups")
    fun getPlaylistGroup(
        @RequestParam(required = false, defaultValue = "DEFAULT") sort: Type,
    ): PlaylistGroupsResponse {
        return playlistService.getGroups(sort).let(::PlaylistGroupsResponse)
    }

    @GetMapping("/api/v1/playlists/{id}")
    fun getPlaylist(
        @PathVariable id: Long,
        user: User?,
    ): PlaylistResponse {
        val playlist = playlistService.get(id)

        val liked = if (user == null) {
            false
        } else {
            playlistViewService.create(playlistId = id, userId = user.id)
            playlistLikeService.exists(playlistId = id, userId = user.id)
        }

        return PlaylistResponse(playlist, liked)
    }

    @GetMapping("/api/v2/playlists/{id}")
    fun getPlaylistV2(
        @PathVariable id: Long,
        user: User?,
    ): PlaylistResponse {
        val liked = threads.submit<Boolean> {
            if (user == null) {
                false
            } else {
                playlistViewService.create(playlistId = id, userId = user.id)
                playlistLikeService.exists(playlistId = id, userId = user.id)
            }
        }

        val playlist = playlistService.get(id)

        return PlaylistResponse(playlist, liked.get())
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
        val status = when (e) {
            is PlaylistNotFoundException, is PlaylistNeverLikedException -> 404
            is PlaylistAlreadyLikedException -> 409
        }

        return ResponseEntity.status(status).build()
    }
}

data class PlaylistGroupsResponse(val groups: List<PlaylistGroup>)

data class PlaylistResponse(val playlist: Playlist, val isLiked: Boolean)

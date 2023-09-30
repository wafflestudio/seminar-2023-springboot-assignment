package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl (
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val playlistRepository: PlaylistRepository,
    private val userRepository: UserRepository
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikeRepository.existsByPlaylistIdAndUserId(playlistId, userId)
    }

    override fun create(playlistId: Long, userId: Long) {
        val (playlist, user) = fetchPlaylistAndUser(playlistId, userId)

        if (exists(playlistId, userId)) {
            throw PlaylistAlreadyLikedException()
        }

        playlistLikeRepository.save(
            PlaylistLikeEntity(
                playlist = playlist,
                user = user
            )
        )
    }

    override fun delete(playlistId: Long, userId: Long) {
        if (!exists(playlistId, userId)) {
            throw PlaylistNeverLikedException()
        }

        val likedPlaylist = playlistLikeRepository.findByPlaylistIdAndUserId(playlistId, userId)

        playlistLikeRepository.delete(likedPlaylist)
    }

    private fun fetchPlaylistAndUser(playlistId: Long, userId: Long): Pair<PlaylistEntity, UserEntity> {
        val playlist = playlistRepository.findById(playlistId)
            .orElseThrow { PlaylistNotFoundException() }

        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException() }

        return Pair(playlist, user)
    }
}

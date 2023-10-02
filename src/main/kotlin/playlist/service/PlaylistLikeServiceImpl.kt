package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val userRepository: UserRepository,
    private val playlistRepository: PlaylistRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikeRepository.findByPlaylistIdAndUserId(playlistId, userId) != null
    }

    override fun create(playlistId: Long, userId: Long) {
        if (exists(playlistId, userId)) {
            throw PlaylistAlreadyLikedException()
        } else {
            val playlist = playlistRepository.findById(playlistId).orElseThrow { PlaylistNotFoundException() }
            val user = userRepository.findById(userId).get()

            val playlistLike = PlaylistLikeEntity(playlist = playlist, user = user)
            playlistLikeRepository.save(playlistLike)
        }


    }

    override fun delete(playlistId: Long, userId: Long) {
        if (!exists(playlistId, userId)) {
            throw PlaylistNeverLikedException()
        }

        val playlist = playlistLikeRepository.findByPlaylistIdAndUserId(playlistId, userId)
            ?: throw PlaylistNotFoundException()
        playlistLikeRepository.delete(playlist)
    }
}

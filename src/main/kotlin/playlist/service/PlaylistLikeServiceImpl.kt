package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val userRepository: UserRepository,
    private val playlistRepository: PlaylistRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {

        return playlistLikeRepository.existsByPlaylistIdAndUserId(playlistId, userId)

    }

    override fun create(playlistId: Long, userId: Long) {
        if (!exists(playlistId, userId)) {
            val user = userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found") }
            val playlist = playlistRepository.findById(playlistId) .orElseThrow { PlaylistNotFoundException() }

            val playlistLike = PlaylistLikeEntity(playlist = playlist, user = user)
            playlistLikeRepository.save(playlistLike)
        }else{
            throw PlaylistAlreadyLikedException()
        }

    }

    override fun delete(playlistId: Long, userId: Long) {
        if (!exists(playlistId, userId)) {
            throw PlaylistNeverLikedException()
        }

        playlistLikeRepository.deleteByPlaylistIdAndUserId(playlistId, userId)


    }
}

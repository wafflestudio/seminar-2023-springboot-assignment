package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl (
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val playlistRepository: PlaylistRepository,
    private val userRepository: UserRepository
): PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikeRepository.existsByIdAndUserId(playlistId, userId)
    }

    private fun getPlaylistLike(playlistId: Long, userId: Long): PlaylistLikeEntity {
        val playlist = playlistRepository.findById(playlistId)
            .orElseThrow { throw PlaylistNotFoundException() }
        val user = userRepository.getReferenceById(userId)
        return PlaylistLikeEntity(playlist=playlist, user=user)
    }

    override fun create(playlistId: Long, userId: Long) {
        if (this.exists(playlistId, userId)) {
            throw PlaylistAlreadyLikedException()
        }
        val playlistLike = this.getPlaylistLike(playlistId, userId)
        playlistLikeRepository.save(playlistLike)
    }

    override fun delete(playlistId: Long, userId: Long) {
        if (!this.exists(playlistId, userId)) {
            throw PlaylistNeverLikedException()
        }
        val playlistLike = this.getPlaylistLike(playlistId, userId)
        playlistLikeRepository.delete(playlistLike)
    }

}

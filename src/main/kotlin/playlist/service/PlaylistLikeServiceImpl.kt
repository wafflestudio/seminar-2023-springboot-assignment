package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val userRepository: UserRepository,
) : PlaylistLikeService {
    override fun exists(playlistId: Long, userId: Long): Boolean =
        playlistLikeRepository.existsByPlaylistIdAndUserId(playlistId, userId)

    @Transactional
    override fun create(playlistId: Long, userId: Long) {
        val playlist = playlistRepository.findByIdOrNull(playlistId) ?: throw PlaylistNotFoundException()
        val user = userRepository.findByIdOrNull(userId)!!

        if (exists(playlistId, userId)) throw PlaylistAlreadyLikedException()

        playlistLikeRepository.save(PlaylistLikeEntity(playlist = playlist, user = user))
    }

    @Transactional
    override fun delete(playlistId: Long, userId: Long) {
        val playlistLike = playlistLikeRepository.findByPlaylistIdAndUserId(playlistId, userId)
            ?: throw PlaylistNeverLikedException()

        playlistLikeRepository.delete(playlistLike)
    }
}

package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val userRepository: UserRepository
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikeRepository
            .existsByPlaylistIdAndUserId(playlistId, userId)
    }

    override fun create(playlistId: Long, userId: Long) {
        if (exists(playlistId, userId)) {
            throw PlaylistAlreadyLikedException()
        }
        val playlist = playlistRepository.findById(playlistId)
            .orElseThrow{ PlaylistNotFoundException() }
        val user = userRepository.findById(userId).get()
        playlistLikeRepository.save(
            PlaylistLikeEntity(user = user, playlist = playlist)
        )
    }

    override fun delete(playlistId: Long, userId: Long) {
        if (!exists(playlistId, userId)) {
            throw PlaylistNeverLikedException()
        }
        playlistLikeRepository
            .deleteByPlaylistIdAndUserId(playlistId, userId)
    }
}

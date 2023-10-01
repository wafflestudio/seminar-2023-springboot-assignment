package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val userRepository: UserRepository,
    private val playlistLikesRepository: PlaylistLikesRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikesRepository.existsByPlaylistIdAndUserId(playlistId, userId)
    }

    override fun create(playlistId: Long, userId: Long) {
        val playlist = playlistRepository.findByIdWithJoinFetch(playlistId) ?: throw PlaylistNotFoundException()

        if (exists(playlistId, userId)) {
            throw PlaylistAlreadyLikedException()
        }

        val user = userRepository.findById(userId).get()
        playlistLikesRepository.save(PlaylistLikesEntity(playlist = playlist, user = user))
    }

    @Transactional
    override fun delete(playlistId: Long, userId: Long) {
        playlistRepository.findByIdWithJoinFetch(playlistId) ?: throw PlaylistNotFoundException()

        if (!exists(playlistId, userId)) {
            throw PlaylistNeverLikedException()
        }

        playlistLikesRepository.deleteByPlaylistIdAndUserId(playlistId, userId)
    }

}

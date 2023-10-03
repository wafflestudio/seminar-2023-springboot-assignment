package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val playlistRepository: PlaylistRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikeRepository.findByPlaylistIdAndUserId(playlistId = playlistId, userId = userId) != null
    }

    override fun create(playlistId: Long, userId: Long) {
        if (playlistRepository.findById(playlistId).isEmpty) {
            throw PlaylistNotFoundException()
        }

        if (exists(playlistId = playlistId, userId = userId)) {
            throw PlaylistAlreadyLikedException()
        }

        playlistLikeRepository.save(
            PlaylistLikeEntity(
                playlistId = playlistId,
                userId = userId
            )
        )
    }

    @Synchronized
    override fun createSynchronized(playlistId: Long, userId: Long) {
        create(playlistId, userId)
    }

    override fun delete(playlistId: Long, userId: Long) {
        val like = playlistLikeRepository.findByPlaylistIdAndUserId(playlistId = playlistId, userId = userId)
            ?: throw PlaylistNeverLikedException()

        playlistLikeRepository.delete(like)
    }
}

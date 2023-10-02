package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class PlaylistLikeServiceImpl(
    private val userRepository: UserRepository,
    private val playlistRepository: PlaylistRepository,
    private val playlistLikeRepository: PlaylistLikeRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean = playlistLikeRepository.existsByPlaylistIdAndUserId(playlistId, userId)

    override fun create(playlistId: Long, userId: Long) {
        if (playlistLikeRepository.existsByPlaylistIdAndUserId(playlistId, userId)) {
            throw PlaylistAlreadyLikedException()
        }

        val user = userRepository.findById(userId).get()
        val playlist = playlistRepository.findById(playlistId).getOrNull() ?: throw PlaylistNotFoundException()

        playlistLikeRepository.save(PlaylistLikeEntity(user = user, playlist = playlist))
    }

    override fun delete(playlistId: Long, userId: Long) {
        if (!playlistLikeRepository.existsByPlaylistIdAndUserId(playlistId, userId)) {
            throw PlaylistNeverLikedException()
        }

        playlistLikeRepository.deleteByPlaylistIdAndUserId(playlistId, userId)
    }
}

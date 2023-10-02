package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import com.wafflestudio.seminar.spring2023.user.service.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    val playlistLikesRepository: PlaylistLikesRepository,
    val playlistRepository: PlaylistRepository,
    val userRepository: UserRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikesRepository.existsByPlaylistIdAndUserId(playlistId, userId)
    }

    override fun create(playlistId: Long, userId: Long) {
        if (exists(playlistId, userId)) throw PlaylistAlreadyLikedException()
        val playlistEntity = playlistRepository.findById(playlistId).orElseThrow {
            PlaylistNotFoundException()
        }
        val userEntity = userRepository.findById(userId).orElseThrow {
            UserNotFoundException()
        }
        playlistLikesRepository.save(
            PlaylistLikesEntity(
                playlist = playlistEntity,
                    user = userEntity,
            )
        )
    }

    override fun delete(playlistId: Long, userId: Long) {
        val playlistLikesEntity = playlistLikesRepository.findByPlaylistIdAndUserId(playlistId, userId)
                ?: throw PlaylistNeverLikedException()
        playlistLikesRepository.delete(
                playlistLikesEntity
        )
    }
}

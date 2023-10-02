package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.service.UserException.*
import com.wafflestudio.seminar.spring2023.user.service.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
        private val playlistLikeRepository: PlaylistLikeRepository,
        private val playlistRepository: PlaylistRepository,
        private val userRepository: UserRepository) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        val checkRes = playlistLikeRepository.findByUserIdAndPlaylistId(playlistId, userId) ?: return false
        return true
    }

    override fun create(playlistId: Long, userId: Long) {
        if (exists(playlistId, userId)) throw PlaylistAlreadyLikedException()
        playlistLikeRepository.save(
                PlaylistLikeEntity(
                        playlist = playlistRepository.findByplId(playlistId)?:throw PlaylistNotFoundException(),
                        user = userRepository.findByIdEquals(userId)?: throw UserNotFoundException()
                )
        )
    }

    override fun delete(playlistId: Long, userId: Long) {
        if (!exists(playlistId, userId)) throw PlaylistNeverLikedException()
        playlistLikeRepository.delete(
                PlaylistLikeEntity(
                        playlist = playlistRepository.findByplId(playlistId)?:throw PlaylistNotFoundException(),
                        user = userRepository.findByIdEquals(userId)?: throw UserNotFoundException()
                )
        )
    }
}

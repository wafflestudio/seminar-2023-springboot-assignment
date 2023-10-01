package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistsRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import com.wafflestudio.seminar.spring2023.user.service.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
        private val playlistLikesRepository: PlaylistLikesRepository,
        private val playlistsRepository: PlaylistsRepository,
        private val userRepository: UserRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikesRepository.findByPlaylistIdAndUserId(playlistId, userId) != null
    }

    override fun create(playlistId: Long, userId: Long) {
        val playlistEntity = playlistsRepository.findPlaylistsEntityById(id = playlistId)?: throw PlaylistNotFoundException()
        val userEntity = userRepository.findById(userId).orElseThrow {
            throw UserNotFoundException()
        }

        if (playlistLikesRepository.findByPlaylistIdAndUserId(playlistId, userId) != null) {
            throw PlaylistAlreadyLikedException()
        }

        playlistLikesRepository.save(PlaylistLikesEntity(playlist = playlistEntity, user = userEntity))
    }

    override fun delete(playlistId: Long, userId: Long) {
        val playlistLikesEntity = playlistLikesRepository.findByPlaylistIdAndUserId(playlistId, userId)?: throw PlaylistNeverLikedException()
        playlistLikesRepository.delete(playlistLikesEntity)
    }
}

package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
        private val playlistLikeRepository: PlaylistLikeRepository,
        private val playlistRepository: PlaylistRepository,
        private val userRepository: UserRepository
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        val playlistEntity = playlistRepository.getReferenceById(playlistId)
        val userEntity = userRepository.getReferenceById(userId)
        return playlistLikeRepository.existsByPlaylistAndUser(playlistEntity, userEntity)
    }

    override fun create(playlistId: Long, userId: Long) {
        if (!playlistRepository.existsById(playlistId)) throw PlaylistNotFoundException()
        if (exists(playlistId, userId)) throw PlaylistAlreadyLikedException()
        val playlistEntity = playlistRepository.getReferenceById(playlistId)
        val userEntity = userRepository.getReferenceById(userId)
        playlistLikeRepository.save(PlaylistLikeEntity(
                id = 0L,
                playlist = playlistEntity,
                user = userEntity
        ))
    }

    override fun delete(playlistId: Long, userId: Long) {
        if (!exists(playlistId, userId)) throw PlaylistNeverLikedException()
        val playlistEntity = playlistRepository.getReferenceById(playlistId)
        val userEntity = userRepository.getReferenceById(userId)
        playlistLikeRepository.deleteByPlaylistAndUser(playlistEntity, userEntity)
    }
}

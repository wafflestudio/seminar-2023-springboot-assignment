package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class PlaylistLikeServiceImpl (
    private val playlistRepository: PlaylistRepository,
    private val userRepository: UserRepository,
    private val playlistLikesRepository: PlaylistLikesRepository
        ) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        if (playlistRepository.findById(playlistId).isEmpty) {
            throw PlaylistNotFoundException()
        }
        // findByIdWithUsers() return empty list if there is no user-likes-playlist relationship (INNER JOIN)
        val playlistEntity = playlistRepository.findByIdWithUsers(playlistId) ?: return false
        return playlistEntity.playlistLikes.any { it.user.id == userId }
    }

    override fun create(playlistId: Long, userId: Long) {
        if (exists(playlistId, userId)) {
            throw PlaylistAlreadyLikedException()
        }
        val playlistEntity = playlistRepository.findById(playlistId).getOrElse { throw PlaylistNotFoundException() }
        val userEntity = userRepository.findById(userId).getOrElse { throw RuntimeException("User Not Found") }
        playlistLikesRepository.save(PlaylistLikesEntity(1L, playlistEntity, userEntity))
    }

    override fun delete(playlistId: Long, userId: Long) {
        if (!exists(playlistId, userId)) {
            throw PlaylistNeverLikedException()
        }
        val playlistEntity = playlistRepository.findById(playlistId).getOrElse { throw PlaylistNotFoundException() }
        val likeRecord = playlistLikesRepository.findByPlaylist(playlistEntity)
            .filter { it.user.id == userId }.getOrNull(0) ?: throw PlaylistNeverLikedException()
        playlistLikesRepository.delete(likeRecord)
    }
}

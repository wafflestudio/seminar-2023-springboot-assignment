package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.*
import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import com.wafflestudio.seminar.spring2023.user.service.UserNotFoundException
import org.springframework.stereotype.Service
import java.nio.file.attribute.UserPrincipalNotFoundException
import kotlin.jvm.optionals.getOrElse

@Service
class PlaylistLikeServiceImpl(
        private val playlistLikesRepository: PlaylistLikesRepository,
        private val playlistRepository: PlaylistRepository,
        private val userRepository: UserRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikesRepository.findByPlaylistIdAndUserId(playlistId, userId) != null
    }

    override fun create(playlistId: Long, userId: Long) {
        if(exists(playlistId, userId)) throw PlaylistAlreadyLikedException()
        val playlist = playlistRepository.findById(playlistId).getOrElse { throw PlaylistNotFoundException() }
        val user = userRepository.findById(userId).getOrElse { throw UserNotFoundException() }
        playlistLikesRepository.save(PlaylistLikesEntity(0L, playlist, user))
    }

    override fun delete(playlistId: Long, userId: Long) {
        val playlistLikesEntity = playlistLikesRepository.findByPlaylistIdAndUserId(playlistId, userId)?: throw PlaylistNeverLikedException()
        playlistLikesRepository.delete(playlistLikesEntity)
    }
}

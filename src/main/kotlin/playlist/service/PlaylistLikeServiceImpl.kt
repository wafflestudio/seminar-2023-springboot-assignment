package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import com.wafflestudio.seminar.spring2023.user.service.AuthenticateException
import com.wafflestudio.seminar.spring2023.user.service.UserException
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val playlistRepository: PlaylistRepository,
    private val userRepository: UserRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        playlistLikeRepository.findByPlaylistIdAndUserId(playlistId, userId)?:return false
        return true
    }

    override fun create(playlistId: Long, userId: Long) {
        if(exists(playlistId, userId)){
            throw PlaylistAlreadyLikedException()
        }

        val playlistEntity = playlistRepository.findByIdEquals(playlistId) ?: throw PlaylistNotFoundException()
        val userEntity = userRepository.findByIdEquals(userId) ?:throw AuthenticateException()


        playlistLikeRepository.save(
            PlaylistLikeEntity(
                user = userEntity,
                playlist = playlistEntity
            )
        )
    }

    override fun delete(playlistId: Long, userId: Long) {
        if(!exists(playlistId,userId)){
            throw PlaylistNeverLikedException()
        }
        val playlistLikeEntity = playlistLikeRepository.findByPlaylistIdAndUserId(playlistId,userId)
        if (playlistLikeEntity != null) {
            playlistLikeRepository.delete(playlistLikeEntity)
        }
    }
}

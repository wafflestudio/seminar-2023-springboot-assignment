package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.apache.catalina.User
import org.springframework.stereotype.Service
import java.nio.file.attribute.UserPrincipalNotFoundException

@Service
class PlaylistLikeServiceImpl(
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val playlistRepository: PlaylistRepository,
    private val userRepository: UserRepository
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikeRepository.existsByPlaylistIdAndUserId(playlistId, userId)
    }

    override fun create(playlistId: Long, userId: Long) {
        val playlistEntity = playlistRepository.findById(playlistId).orElseThrow{ PlaylistNotFoundException()}
        val userEntity = userRepository.findById(userId).orElseThrow{ UserNotFoundException() }

        if (this.exists(playlistId,userId)){
            throw PlaylistAlreadyLikedException()
        }
        playlistLikeRepository.save(PlaylistLikesEntity(playlist = playlistEntity, user = userEntity))
    }

    override fun delete(playlistId: Long, userId: Long) {
        if (this.exists(playlistId,userId)){
            playlistLikeRepository.deleteByPlaylistIdAndUserId(playlistId = playlistId,userId= userId)
        }
        else{
            throw PlaylistNeverLikedException()
        }
    }
}

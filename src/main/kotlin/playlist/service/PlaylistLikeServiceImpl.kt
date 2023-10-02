package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import com.wafflestudio.seminar.spring2023.user.service.UserNotFoundException
import org.springframework.stereotype.Service
import java.nio.file.attribute.UserPrincipalNotFoundException

@Service
class PlaylistLikeServiceImpl(
        private val playlistLikeRepository: PlaylistLikeRepository,
        private val playlistRepository : PlaylistRepository,
        private val userRepository: UserRepository
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        val userEntity = userRepository.findById(userId).orElseThrow{ UserNotFoundException() }
        val playlistEntity = playlistRepository.findById(playlistId).orElseThrow { PlaylistNotFoundException() }
        return playlistLikeRepository.existsByPlaylistAndUser(playlist = playlistEntity, user = userEntity)
    }

    override fun create(playlistId: Long, userId: Long) {
        if(exists(playlistId,userId)) throw PlaylistAlreadyLikedException()
        val userEntity = userRepository.findById(userId).orElseThrow{ UserNotFoundException() }
        val playlistEntity = playlistRepository.findById(playlistId).orElseThrow { PlaylistNotFoundException() }
        playlistLikeRepository.save(PlaylistLikeEntity(
                playlist = playlistEntity,
                user = userEntity)
        )
    }

    override fun delete(playlistId: Long, userId: Long) {
        if(!exists(playlistId,userId)) throw PlaylistNeverLikedException()
        val userEntity = userRepository.findById(userId).orElseThrow{ UserNotFoundException() }
        val playlistEntity = playlistRepository.findById(playlistId).orElseThrow { PlaylistNotFoundException() }
        playlistLikeRepository.deleteByPlaylistAndUser(playlist = playlistEntity, user = userEntity)
    }
}

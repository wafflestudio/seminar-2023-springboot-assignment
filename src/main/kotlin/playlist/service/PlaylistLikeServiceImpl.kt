package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val userRepository: UserRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        //TODO()
        val playlistEntity = playlistRepository.findById(playlistId).orElseThrow{PlaylistNotFoundException()}
        return playlistLikeRepository.findByPlaylistAndUserId(playlistEntity,userId) != null
    }

    override fun create(playlistId: Long, userId: Long) {
        //TODO()
        if(exists(playlistId,userId)) throw PlaylistAlreadyLikedException()
        val playlistEntity = playlistRepository.findById(playlistId).get()
        val userEntity = userRepository.findById(userId).get()
        playlistLikeRepository.save(PlaylistLikeEntity(0L,playlistEntity,userEntity))
    }

    override fun delete(playlistId: Long, userId: Long) {
        //TODO()
        val playlistEntity = playlistRepository.findById(playlistId).orElseThrow{PlaylistNotFoundException()}
        val playlistLikeEntity = playlistLikeRepository.findByPlaylistAndUserId(playlistEntity,userId)?:throw PlaylistNeverLikedException()
        playlistLikeRepository.delete(playlistLikeEntity)
    }
}

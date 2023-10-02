package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
        private val playlistLikeRepository: PlaylistLikeRepository,
        private val playlistRepository: PlaylistRepository,
        private val userRepository: UserRepository
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        //TODO()
        val playlistLike = playlistLikeRepository.findPlaylistLikeEntityByPlaylistId(playlistId).filter { it.user.id == userId }
        return !playlistLike.isEmpty()
    }

    override fun create(playlistId: Long, userId: Long) {
        //TODO()
        val playlist = playlistRepository.findPlaylistEntityById(playlistId) ?: throw PlaylistNotFoundException()
        val playlistLike = playlistLikeRepository.findPlaylistLikeEntityByPlaylistId(playlistId)
        playlistLike.forEach { if (it.user.id == userId) {
            throw PlaylistAlreadyLikedException() }
        }
        playlistLikeRepository.save(PlaylistLikeEntity(
                playlist = playlist,
                user = userRepository.findById(userId).get()
        ))
    }

    override fun delete(playlistId: Long, userId: Long) {
        //TODO()
        val playlist = playlistRepository.findPlaylistEntityById(playlistId) ?: throw PlaylistNotFoundException()
        val playlistLike = playlistLikeRepository.findPlaylistLikeEntityByPlaylistId(playlistId).filter { it.user.id == userId }
        if (playlistLike.isEmpty()) {
            throw PlaylistNeverLikedException()
        }
        playlistLikeRepository.delete(PlaylistLikeEntity(
                playlist = playlist,
                user = userRepository.findById(userId).get()
        ))
    }
}

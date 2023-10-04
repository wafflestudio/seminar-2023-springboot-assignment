package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val playlistRepository: PlaylistRepository,
    private val userRepository: UserRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikeRepository.existsByPlaylist_IdAndUser_Id(playlistId, userId)
    }

    override fun create(playlistId: Long, userId: Long) {
        val playlist = playlistRepository.findByIdWithJoinFetch(playlistId)
        val user = userRepository.findById(userId)
        if (playlist == null) {
            throw PlaylistNotFoundException()
        }
        if (exists(playlistId, userId)) {
            throw PlaylistAlreadyLikedException()
        }

        val entity = playlistLikeRepository.save(
            PlaylistLikeEntity(
                playlist = playlist,
                user = user.get(),
            )
        )
    }

    override fun delete(playlistId: Long, userId: Long) {
        val playlistLike = playlistLikeRepository.findByPlaylist_IdAndUser_Id(playlistId, userId)

        if (playlistLike == null) {
            throw PlaylistNeverLikedException()
        }

        playlistLikeRepository.delete(playlistLike)

    }
}

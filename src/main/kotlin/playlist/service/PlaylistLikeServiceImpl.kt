package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val userRepository: UserRepository,
    private val playlistLikeRepository: PlaylistLikeRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikeRepository.existsByPlaylistIdAndUserId(playlistId, userId)
    }

    override fun create(playlistId: Long, userId: Long) {
        val playlist = playlistRepository.findByIdWithSongs(playlistId) ?: throw PlaylistNotFoundException()
        if (exists(playlistId, userId)) {
            throw PlaylistAlreadyLikedException()
        }

        val user = userRepository.findById(userId).get()
        playlistLikeRepository.save(PlaylistLikeEntity(playlist = playlist, user = user))
    }

    @Transactional
    override fun delete(playlistId: Long, userId: Long) {
        playlistRepository.findByIdWithSongs(playlistId) ?: throw PlaylistNotFoundException()

        if (!exists(playlistId, userId)) {
            throw PlaylistNeverLikedException()
        }

        playlistLikeRepository.deleteByPlaylistIdAndUserId(playlistId, userId)
    }

}


        /*
        val playlist = playlistRepository.findById(playlistId).orElse(null)
        val user = userRepository.findById(userId).get()
        if (playlist == null) {
            throw PlaylistNotFoundException()
        }
        if (exists(playlistId, userId)) {
            throw PlaylistAlreadyLikedException()
        }
        playlistLikeRepository.save(PlaylistLikeEntity(playlist = playlist, user = user))
    }
    @Transactional

    override fun delete(playlistId: Long, userId: Long) {
        if (!exists(playlistId, userId)) {
            throw PlaylistNeverLikedException()
        }
        playlistLikeRepository.deleteByPlaylistIdAndUserId(playlistId, userId)
    }

}
*/

package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val userRepository: UserRepository,
    private val playlistLikesRepository: PlaylistLikeRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        TODO()
    }

    override fun create(playlistId: Long, userId: Long) {
        TODO()
    }

    override fun delete(playlistId: Long, userId: Long) {
        TODO()
    }
}

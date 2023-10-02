package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaylistLikeServiceImpl(
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val playlistRepository: PlaylistRepository
) : PlaylistLikeService {

    @Transactional(readOnly = true)
    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikeRepository.findByPlaylistIdAndUserId(playlistId, userId) != null
    }

    @Transactional
    override fun create(playlistId: Long, userId: Long) {

        // 1. 존재하지 않는 플레이리스트에 좋아요를 누를 수 없다.
        if (playlistRepository.findById(playlistId).isEmpty) {
            throw PlaylistNotFoundException()
        }

        // 2. 이미 좋아요를 누른 플레이리스트는 또 좋아요를 누를 수 없다.
        if (exists(playlistId, userId)) {
            throw PlaylistAlreadyLikedException()
        }

        playlistLikeRepository.save(PlaylistLikeEntity(
            playlist = PlaylistEntity(id = playlistId),
            user = UserEntity(id = userId)
        ))
    }
    @Transactional
    override fun delete(playlistId: Long, userId: Long) {
        // 1. 좋아요를 누르지 않은 플레이리스트에 좋아요 취소를 누를 수 없다.
        if (!exists(playlistId, userId)) {
            throw PlaylistNeverLikedException()
        }

        playlistLikeRepository.deleteByPlaylistIdAndUserId(playlistId, userId)
    }
}

package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikesRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val playlistLikesRepository: PlaylistLikesRepository
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        if (!playlistRepository.existsById(playlistId)) {
            throw PlaylistNotFoundException()
        }

        return playlistLikesRepository.findUserPlaylistLike(playlistId, userId) != null
    }

    override fun create(playlistId: Long, userId: Long) {
        if (!playlistRepository.existsById(playlistId)) {
            throw PlaylistNotFoundException()
        }

        if (playlistLikesRepository.findUserPlaylistLike(playlistId, userId) != null) {
            throw PlaylistAlreadyLikedException()
        }

        playlistLikesRepository.save(PlaylistLikesEntity(playlist_id = playlistId, user_id = userId))
    }

    override fun delete(playlistId: Long, userId: Long) {
        if (!playlistRepository.existsById(playlistId)) {
            throw PlaylistNotFoundException()
        }

        if (playlistLikesRepository.findUserPlaylistLike(playlistId, userId) == null) {
            throw PlaylistNeverLikedException()
        }

        playlistLikesRepository.delete(PlaylistLikesEntity(playlist_id = playlistId, user_id = userId))
    }
}

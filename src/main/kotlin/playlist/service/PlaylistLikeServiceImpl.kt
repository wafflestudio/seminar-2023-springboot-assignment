package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import com.wafflestudio.seminar.spring2023.user.service.SignInUserNotFoundException
import org.springframework.stereotype.Service
@Service
class PlaylistLikeServiceImpl(
    private val playlistLikeRepository : PlaylistLikeRepository,
    private val playlistRepository: PlaylistRepository,
    private val userRepository: UserRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        val entity = playlistLikeRepository.findByPlaylistOfLike_IdAndUser_Id(playlistId, userId)
        return entity != null
    }

    override fun create(playlistId: Long, userId: Long) {
        if(!exists(playlistId, userId)){
            val playlistOfLike =  playlistRepository.findById(playlistId)
                .orElseThrow{PlaylistNotFoundException()}
            val user = userRepository.findById(userId).orElseThrow{SignInUserNotFoundException()}
            val entity = playlistLikeRepository.save(
                PlaylistLikeEntity(
                    playlistOfLike = playlistOfLike,
                    user = user,
                )
            )
        }
        else{
            throw PlaylistAlreadyLikedException()
        }
    }

    override fun delete(playlistId: Long, userId: Long) {
        val entity = playlistLikeRepository.findByPlaylistOfLike_IdAndUser_Id(playlistId, userId) ?: throw PlaylistNeverLikedException()
        playlistLikeRepository.delete(entity)
    }
}

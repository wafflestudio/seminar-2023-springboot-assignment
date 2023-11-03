package com.wafflestudio.seminar.spring2023.playlist.service

<<<<<<< HEAD
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
=======
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1
import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl(
<<<<<<< HEAD
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
=======
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val playlistRepository: PlaylistRepository,
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean {
        return playlistLikeRepository.findByPlaylistIdAndUserId(playlistId = playlistId, userId = userId) != null
    }

    override fun create(playlistId: Long, userId: Long) {
        if (playlistRepository.findById(playlistId).isEmpty) {
            throw PlaylistNotFoundException()
        }

        if (exists(playlistId = playlistId, userId = userId)) {
            throw PlaylistAlreadyLikedException()
        }

        playlistLikeRepository.save(
            PlaylistLikeEntity(
                playlistId = playlistId,
                userId = userId
            )
        )
    }

    @Synchronized
    override fun createSynchronized(playlistId: Long, userId: Long) {
        create(playlistId, userId)
    }

    override fun delete(playlistId: Long, userId: Long) {
        val like = playlistLikeRepository.findByPlaylistIdAndUserId(playlistId = playlistId, userId = userId)
            ?: throw PlaylistNeverLikedException()

        playlistLikeRepository.delete(like)
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1
    }
}

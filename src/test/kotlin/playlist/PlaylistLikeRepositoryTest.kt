package com.wafflestudio.seminar.spring2023.playlist

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PlaylistLikeRepositoryTest @Autowired constructor(
    private val playlistLikeRepository: PlaylistLikeRepository
){

    @Test
    fun `저장`() {
        playlistLikeRepository.save(
            PlaylistLikeEntity(
                playlist = PlaylistEntity(id = 1L),
                user = UserEntity(id = 1L)
            )
        )
    }

    @Test
    fun `찾기`() {
        println(playlistLikeRepository.findByPlaylistIdAndUserId(1L, 1L))
    }
}
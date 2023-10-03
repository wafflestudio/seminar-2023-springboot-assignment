package com.wafflestudio.seminar.spring2023.playlist

import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistAlreadyLikedException
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistLikeService
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistNeverLikedException
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

//playlistlikeservice 구현시 지워야 할 오류에 대해서 명세
@Transactional
@SpringBootTest
class PlaylistLikeServiceTest @Autowired constructor(
    private val playlistLikeService: PlaylistLikeService,
) {

    @Test
    fun `존재하지 않는 플레이리스트에 좋아요를 누를 수 없다`() {
        assertThrows<PlaylistNotFoundException> {
            playlistLikeService.create(
                playlistId = 404404404L,
                userId = 1L
            )
        }
    }

    @Test
    fun `이미 좋아요를 누른 플레이리스트는 또 좋아요를 누를 수 없다`() {
        playlistLikeService.create(
            playlistId = 1L,
            userId = 1L
        )

        assertThrows<PlaylistAlreadyLikedException> {
            playlistLikeService.create(
                playlistId = 1L,
                userId = 1L
            )
        }
    }

    @Test
    fun `좋아요를 누르지 않은 플레이리스트에 좋아요 취소를 누를 수 없다`() {
        assertThrows<PlaylistNeverLikedException> {
            playlistLikeService.delete(
                playlistId = 1L,
                userId = 1L
            )
        }
    }

    @Test
    fun `좋아요 여부 조회`() {
        playlistLikeService.create(
            playlistId = 1L,
            userId = 1L
        )

        assertThat(playlistLikeService.exists(playlistId = 1L, userId = 1L)).isTrue
        assertThat(playlistLikeService.exists(playlistId = 2L, userId = 1L)).isFalse()
    }
}

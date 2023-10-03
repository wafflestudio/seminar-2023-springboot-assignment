package com.wafflestudio.seminar.spring2023.week2

import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistAlreadyLikedException
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistLikeService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

@Transactional
@SpringBootTest
class SynchronizationTest @Autowired constructor(
    private val playlistLikeService: PlaylistLikeService,
) {
    private val threadPool = Executors.newFixedThreadPool(4)

    @Test
    fun `동기화 없이 좋아요 따닥 생성`() {
        val taskA = threadPool.submit { playlistLikeService.create(playlistId = 1L, userId = 1L) }
        val taskB = threadPool.submit { playlistLikeService.create(playlistId = 1L, userId = 1L) }

        assertDoesNotThrow {
            taskA.get()
            taskB.get()
        }
    }

    @Test
    fun `동기화 사용하여 좋아요 따닥 생성`() {
        val taskA = threadPool.submit { playlistLikeService.createSynchronized(playlistId = 2L, userId = 1L) }
        val taskB = threadPool.submit { playlistLikeService.createSynchronized(playlistId = 2L, userId = 1L) }

        val exception = assertThrows<ExecutionException> {
            taskA.get()
            taskB.get()
        }

        assertThat(exception.cause).isInstanceOf(PlaylistAlreadyLikedException::class.java)
    }
}

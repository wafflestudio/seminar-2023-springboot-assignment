package com.wafflestudio.seminar.spring2023.playlist

import com.wafflestudio.seminar.spring2023.QueryCounter
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistServiceCacheImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
internal class PlaylistServiceCacheImplTest @Autowired constructor(
    private val playlistServiceCache: PlaylistServiceCacheImpl,
    private val queryCounter: QueryCounter
) {

    val ttl = 10L

    @Test
    fun `캐시 저장한 것 가져오기`() {
        val (playlist, queryCount) = queryCounter.count {
            playlistServiceCache.get(2L)
            playlistServiceCache.get(3L)
            playlistServiceCache.get(2L)
        }

        assertThat(queryCount).isEqualTo(2 * 2)


    }

    @Test
    fun `ttl 이 지난 이후에는 cache miss 이므로 다시 캐시 저장하기`() {
        val (playlist, queryCount) = queryCounter.count {
            playlistServiceCache.get(1L)
            Thread.sleep(ttl * 1_000)
            playlistServiceCache.get(1L)
        }

        assertThat(queryCount).isEqualTo(2 * 2)

    }


}
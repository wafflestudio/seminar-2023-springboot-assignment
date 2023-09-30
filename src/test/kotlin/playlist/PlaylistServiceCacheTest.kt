package com.wafflestudio.seminar.spring2023.playlist

import com.wafflestudio.seminar.spring2023.QueryCounter
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import org.assertj.core.api.Assertions.assertThat

@Transactional
@SpringBootTest
class PlaylistServiceCacheTest @Autowired constructor(
    @Qualifier("playlistServiceCacheImpl") private val playlistService: PlaylistService,
    private val queryCounter: QueryCounter
    ) {

    @Test
    fun `플레이리스트 그룹 캐싱`() {
        val (_, queryCount) = queryCounter.count {
            // query
            println(playlistService.getGroups())
            Thread.sleep(5000)
            // No query (Caching)
            println(playlistService.getGroups())
            Thread.sleep(5000)
            // query
            println(playlistService.getGroups())
        }
        assertThat(queryCount).isEqualTo(2)
    }

    @Test
    fun `플레이리스트 캐싱`() {
        val (_, queryCount) = queryCounter.count {
            // 3 queries
            for (i in 1L..3L) { println(playlistService.get(i)) }
            Thread.sleep(5000)
            // No query (Caching)
            for (i in 1L..3L) { println(playlistService.get(i)) }
            Thread.sleep(5000)
            // 3 queries
            for (i in 1L..3L) { println(playlistService.get(i)) }
        }
        assertThat(queryCount).isEqualTo(6)
    }
}
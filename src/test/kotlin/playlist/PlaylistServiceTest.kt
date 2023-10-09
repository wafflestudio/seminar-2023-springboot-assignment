package com.wafflestudio.seminar.spring2023.playlist

import com.wafflestudio.seminar.spring2023.QueryCounter
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistNotFoundException
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class PlaylistServiceTest @Autowired constructor(
    private val playlistService: PlaylistService,
    private val queryCounter: QueryCounter,
) {

    @Test
    fun `오픈 상태의 플레이리스트 그룹을 조회, 연관된 플레이리스트가 없는 경우 결과에서 제외`() {
        val playlistGroups = playlistService.getGroups()

        assertThat(playlistGroups.map { it.id to it.playlists.map { it.id } })
            .isEqualTo(
                listOf(
                    1L to listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L),
                    3L to listOf(10L, 11L, 12L, 13L, 14L, 15L, 16L)
                )
            )
    }

    @Test
    fun `오픈 상태의 플레이리스트 그룹을 조회, 연관된 플레이리스트가 없는 경우 결과에서 제외, 쿼리 횟수는 1개로 제한`() {
        val (playlistGroups, queryCount) = queryCounter.count { playlistService.getGroups() }

        assertThat(playlistGroups.map { it.id to it.playlists.map { it.id } })
            .isEqualTo(
                listOf(
                    1L to listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L),
                    3L to listOf(10L, 11L, 12L, 13L, 14L, 15L, 16L)
                )
            )

        assertThat(queryCount).isLessThanOrEqualTo(1)
    }

    @Test
    fun `플레이리스트 조회`() {
        val playlist = playlistService.get(1L)

        assertThat(playlist).isNotNull

        playlist!!.run {
            assertThat(id).isEqualTo(1L)
            assertThat(title).isEqualTo("Today's Top Hits")
            assertThat(songs.map { it.id })
                .isEqualTo(listOf(2L, 8, 13, 14, 16, 34, 36, 37, 38, 39, 41, 43, 44, 45, 56, 62, 68, 79, 122))

            val songByJungkook = songs.find { it.title == "Seven (feat. Latto) (Explicit Ver.)" }

            assertThat(songByJungkook).isNotNull
            assertThat(songByJungkook!!.artists.map { it.id }).isEqualTo(listOf(8L, 9))
        }
    }

    @Test
    fun `플레이리스트 조회, 쿼리 횟수는 2회로 제한`() {
        val (playlist, queryCount) = queryCounter.count { playlistService.get(1L) }

        assertThat(playlist).isNotNull

        playlist!!.run {
            assertThat(id).isEqualTo(1L)
            assertThat(title).isEqualTo("Today's Top Hits")
            assertThat(songs.map { it.id })
                .isEqualTo(listOf(2L, 8, 13, 14, 16, 34, 36, 37, 38, 39, 41, 43, 44, 45, 56, 62, 68, 79, 122))

            val songByJungkook = songs.find { it.title == "Seven (feat. Latto) (Explicit Ver.)" }

            assertThat(songByJungkook).isNotNull
            assertThat(songByJungkook!!.artists.map { it.id }).isEqualTo(listOf(8L, 9))
        }

        assertThat(queryCount).isLessThanOrEqualTo(2)
    }

    @Test
    fun `존재하지 않는 플레이리스트 조회시 에러 발생`() {
        assertThrows<PlaylistNotFoundException> {
            playlistService.get(404404404L)
        }
    }
}

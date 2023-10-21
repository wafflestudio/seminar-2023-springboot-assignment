package com.wafflestudio.seminar.spring2023.playlist

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistViewRepository
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistBrief
import com.wafflestudio.seminar.spring2023.playlist.service.PlaylistViewService
import com.wafflestudio.seminar.spring2023.playlist.service.SortPlaylist
import com.wafflestudio.seminar.spring2023.playlist.service.SortPlaylist.Type
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class PlaylistViewServiceTest @Autowired constructor(
    private val playlistViewService: PlaylistViewService,
    private val sortPlaylist: SortPlaylist,
    private val playlistRepository: PlaylistRepository,
    private val playlistViewRepository: PlaylistViewRepository,
) {

    @Test
    fun `같은 유저-같은 플레이리스트의 조회 수는 1분에 1개까지만 허용한다`() {
        val beforePlaylist = playlistRepository.findById(1L).get()
        val viewsAt = listOf(
            LocalDateTime.parse("2023-10-24T10:00:00"),
            LocalDateTime.parse("2023-10-24T10:00:59"),
            LocalDateTime.parse("2023-10-24T10:01:01")
        )

        val firstView = playlistViewService.create(playlistId = 1L, userId = 1L, at = viewsAt[0]).get()

        assertThat(firstView).isTrue()
        assertThat(playlistRepository.findById(1L).get().viewCnt).isEqualTo(beforePlaylist.viewCnt + 1)

        val secondView = playlistViewService.create(playlistId = 1L, userId = 1L, at = viewsAt[1]).get()

        assertThat(secondView).isFalse()
        assertThat(playlistRepository.findById(1L).get().viewCnt).isEqualTo(beforePlaylist.viewCnt + 1)

        val thirdView = playlistViewService.create(playlistId = 1L, userId = 1L, at = viewsAt[2]).get()

        assertThat(thirdView).isTrue()
        assertThat(playlistRepository.findById(1L).get().viewCnt).isEqualTo(beforePlaylist.viewCnt + 2)
    }

    @Test
    fun `플레이리스트 조회 요청이 동시에 다수 들어와도, 요청이 들어온 만큼 조회수가 증가해야한다`() {
        val beforePlaylist = playlistRepository.findById(1L).get()

        (1L..8L)
            .map { playlistViewService.create(playlistId = 1L, userId = it) }
            .map { it.get() }

        val afterPlaylist = playlistRepository.findById(1L).get()
        assertThat(afterPlaylist.viewCnt).isEqualTo(beforePlaylist.viewCnt + 8)
    }

    @Test
    fun `플레이리스트 정렬 테스트`() {
        val mockPlaylists = (1L..3L).map {
            PlaylistBrief(
                id = it, title = "mock", subtitle = "mock", image = "mock"
            )
        }

        val now = LocalDateTime.parse("2023-10-24T10:00:00")

        // Playlist(1) 총 조회 수 3, 최근 조회 수 0
        (1L..3L)
            .map { playlistViewService.create(playlistId = 1L, userId = it, at = now.minusHours(2)) }
            .map { it.get() }

        // Playlist(2) 총 조회 수 5, 최근 조회 수 1
        (1L..4L)
            .map { playlistViewService.create(playlistId = 2L, userId = it, at = now.minusHours(2)) }
            .map { it.get() }

        playlistViewService.create(playlistId = 2L, userId = 5L, at = now)

        // Playlist(3) 총 조회 수 2, 최근 조회 수 2
        (1L..2L)
            .map { playlistViewService.create(playlistId = 3L, userId = it, at = now) }
            .map { it.get() }

        assertThat(sortPlaylist.invoke(mockPlaylists, type = Type.DEFAULT)
            .map { it.id })
            .isEqualTo(listOf(1L, 2L, 3L))

        assertThat(sortPlaylist.invoke(mockPlaylists, type = Type.VIEW)
            .map { it.id })
            .isEqualTo(listOf(2L, 1L, 3L))

        assertThat(sortPlaylist.invoke(mockPlaylists, type = Type.HOT, at = now)
            .map { it.id })
            .isEqualTo(listOf(3L, 2L, 1L))
    }

    @BeforeEach
    fun before() {
        // reset playlist viewCnt
        playlistRepository.findAll()
            .forEach {
                it.viewCnt = 0
                playlistRepository.save(it)
            }

        // reset playlistViews
        playlistViewRepository.deleteAll()
    }
}

package com.wafflestudio.seminar.spring2023.customplaylist

import com.wafflestudio.seminar.spring2023.customplaylist.repository.CustomPlaylistRepository
import com.wafflestudio.seminar.spring2023.customplaylist.service.CustomPlaylistNotFoundException
import com.wafflestudio.seminar.spring2023.customplaylist.service.CustomPlaylistService
import com.wafflestudio.seminar.spring2023.customplaylist.service.SongNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.Executors

/***
 * 주의: @Transactional 사용하지 않기. (데이터 롤백, 레이지 로드 등의 이슈를 피할 수 있도록 구현)
 */
@SpringBootTest
class CustomPlaylistServiceTest @Autowired constructor(
    private val customPlaylistService: CustomPlaylistService,
    private val customPlaylistRepository: CustomPlaylistRepository,
) {

    @Test
    fun `커스텀 플레이리스트 생성시, 자동 생성되는 제목은 내 플레이리스트 #${유저의 커스텀 플레이리스트 갯수 + 1}`() {
        val created = customPlaylistService.create(userId = 1L)

        assertThat(created.title).isEqualTo("내 플레이리스트 #1")
        assertThat(created.songCnt).isEqualTo(0)

        val created2 = customPlaylistService.create(userId = 1L)

        assertThat(created2.title).isEqualTo("내 플레이리스트 #2")
        assertThat(created.songCnt).isEqualTo(0)
    }

    @Test
    fun `커스텀 플레이리스트 타이틀 수정`() {
        val created = customPlaylistService.create(userId = 1L)

        val updated = customPlaylistService.patch(userId = 1L, customPlaylistId = created.id, title = "수정")

        assertThat(updated.title).isEqualTo("수정")
    }

    @Test
    fun `내 커스텀 플레이리스트가 아니면 타이틀 수정 시도시, CustomPlaylistNotFoundException이 발생한다`() {
        val created = customPlaylistService.create(userId = 1L)

        assertThrows<CustomPlaylistNotFoundException> {
            customPlaylistService.patch(userId = 2L, customPlaylistId = created.id, title = "수정")
        }
    }

    @Test
    fun `커스텀 플레이리스트에 곡 추가시, songCnt가 올라가고 연결 테이블의 row가 생성된다`() {
        val created = customPlaylistService.create(userId = 1L)

        customPlaylistService.addSong(
            userId = 1L,
            customPlaylistId = created.id,
            songId = 1L
        )

        // songCnt 컬럼 체크
        val customPlaylistEntity = customPlaylistRepository.findById(created.id).get()

        assertThat(customPlaylistEntity.songCnt).isEqualTo(1)

        // customPlaylistSong 연결 테이블 체크
        val customPlaylist = customPlaylistService.get(userId = 1L, customPlaylistId = created.id)

        assertThat(customPlaylist.songs.size).isEqualTo(1)
        assertThat(customPlaylist.songs.first().id).isEqualTo(1L)
    }

    @Test
    fun `커스텀 플레이리스트에 곡 추가 시, 동시성 이슈는 발생하지 않아야 한다`() {
        val created = customPlaylistService.create(userId = 1L)

        val threads = Executors.newFixedThreadPool(10)
        val parallelJobs = (1L..10L).map { songId ->
            threads.submit {
                customPlaylistService.addSong(
                    userId = 1L,
                    customPlaylistId = created.id,
                    songId = songId
                )
            }
        }
        parallelJobs.forEach { it.get() }

        // songCnt 컬럼 체크
        val customPlaylistEntity = customPlaylistRepository.findById(created.id).get()

        assertThat(customPlaylistEntity.songCnt).isEqualTo(10)

        // customPlaylistSong 연결 테이블 체크
        val customPlaylist = customPlaylistService.get(userId = 1L, customPlaylistId = created.id)

        assertThat(customPlaylist.songs.size).isEqualTo(10)
        assertThat(customPlaylist.songs.map { it.id }.toSet()).isEqualTo((1L..10L).toSet())
    }

    @Test
    fun `내 커스텀 플레이리스트가 아니면 곡 추가 시도시, CustomPlaylistNotFoundException이 발생한다`() {
        val created = customPlaylistService.create(userId = 1L)

        assertThrows<CustomPlaylistNotFoundException> {
            customPlaylistService.addSong(userId = 2L, customPlaylistId = created.id, songId = 1L)
        }
    }

    @Test
    fun `존재하지 않는 곡을 추가 시도시, SongFoundException이 발생한다`() {
        val created = customPlaylistService.create(userId = 1L)

        assertThrows<SongNotFoundException> {
            customPlaylistService.addSong(userId = 1L, customPlaylistId = created.id, songId = 404404L)
        }
    }
}

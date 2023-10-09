package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wafflestudio.seminar.spring2023.playlist.controller.PlaylistGroupsResponse
import com.wafflestudio.seminar.spring2023.playlist.controller.PlaylistResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    private val mapper = jacksonObjectMapper()

    @Test
    fun `플레이리스트 그룹 조회`() {
        val response = mvc.get("/api/v1/playlist-groups")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.groups[0].playlists") { hasJsonPath() }
            }
            .andReturn()
            .response

        val playlistGroups = mapper
            .readValue(response.contentAsString, PlaylistGroupsResponse::class.java)
            .groups

        assertThat(playlistGroups.map { it.id to it.playlists.map { it.id } })
            .isEqualTo(
                listOf(
                    1L to listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L),
                    3L to listOf(10L, 11L, 12L, 13L, 14L, 15L, 16L)
                )
            )
    }

    @Test
    fun `플레이리스트 조회`() {
        val response = mvc.get("/api/v1/playlists/1")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.playlist") { hasJsonPath() }
            }
            .andReturn()
            .response

        val playlist = mapper
            .readValue(response.contentAsString, PlaylistResponse::class.java)
            .playlist

        playlist.run {
            assertThat(id).isEqualTo(1L)
            assertThat(title).isEqualTo("Today's Top Hits")
            assertThat(songs.map { it.id })
                .isEqualTo(listOf(2L, 8, 13, 14, 16, 34, 36, 37, 38, 39, 41, 43, 44, 45, 56, 62, 68, 79, 122))

            val songByJungkook = songs.find { it.title == "Seven (feat. Latto) (Explicit Ver.)" }

            assertThat(songByJungkook).isNotNull
            assertThat(songByJungkook!!.artists.map { it.id }).isEqualTo(listOf(8L, 9))
        }

    }
}

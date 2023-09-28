package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {

    val wrongId = 4040404
    val existId = 1
    val likedId = 2
    val unlikedId = 3

    @Test
    fun `플리 그룹 조회에 성공하면 200 응답을 받는다`() {
        mvc.perform(
            get("/api/v1/playlist-groups")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.groups[0].title").value("Spotify 플레이리스트"))
    }

    @Test
    fun `존재하지 않는 플리에 좋아요를 누르면 404 응답을 내려준다`() {
        mvc.perform(
            post("/api/v1/playlists/$wrongId/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(404))

    }

    @Test
    fun `이미 좋아요를 누른 플리에는 좋아요를 누르면 404 응답을 내려준다`() {
        mvc.perform(
            post("/api/v1/playlists/$likedId/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/playlists/$likedId/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(404))
    }

    @Test
    fun `좋아요를 누르지 않은 플리에 좋아요 취소를 누르면 404 응답을 내려준다`() {
        mvc.perform(
            delete("/api/v1/playlists/$unlikedId/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(404))
    }

    @Test
    fun `개별 플레이리스트를 조회할 수 없을 때에는 404 응답을 내려준다`() {

        // bad playlistId
        mvc.perform(
            get("/api/v1/playlists/$wrongId")
        )
            .andExpect(status().`is`(404))

        // 좋아요 처리
        mvc.perform(
            post("/api/v1/playlists/$existId/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(200))

        // 로그인 시 좋아요 확인
        mvc.perform(
            get("/api/v1/playlists/$existId")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.isLiked").value(true))
            .andExpect(jsonPath("$.playlist.title").value("Today's Top Hits"))

        // 비로그인 시, 좋아요는 false로 처리
        mvc.perform(
            get("/api/v1/playlists/$existId")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.isLiked").value(false))
            .andExpect(jsonPath("$.playlist.title").value("Today's Top Hits"))

    }
}

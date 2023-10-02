package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {
    @Test
    fun `플레이리스트 그룹 조회 성공시 200 응답한다`() {
        mvc.perform(
            get("/api/v1/playlist-groups")
        ).andExpect(status().`is`(200))
    }

    @Test
    fun `플레이리스트 조회 실패시 404 응답한다`() {
        mvc.perform(
            get("/api/v1/playlists/1")
        ).andExpect(status().`is`(200))

        mvc.perform(
            get("/api/v1/playlists/10000")
        ).andExpect(status().`is`(404))
    }

    @Test
    fun `존재하지 않는 플레이리스트에 좋아요를 누를시 404 응답한다`() {
        mvc.perform(
            post("/api/v1/playlists/100/likes")
                .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(404))
    }

    @Test
    fun `플레이리스트의 좋아요가 이미 눌려있는 경우 좋아요를 누를시 409 응답한다`() {
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(409))
    }

    @Test
    fun `플레이리스트의 좋아요가 없는 경우 좋아요 취소를 누르면 409 응답한다`() {
        mvc.perform(
            delete("/api/v1/playlists/100/likes")
                .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(409))
    }
}

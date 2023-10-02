package com.wafflestudio.seminar.spring2023.playlist

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {

    @Test
    fun `플레이리스트 그룹 조회`() {
        mvc.perform(get("/api/v1/playlist-groups"))
            .andExpect(status().isOk)
        // Add assertions for the expected response, e.g., jsonPath("$.[0].id").value(1))
    }

    @Test
    fun `미로그인 상태로 플레이리스트 조회`() {
        mvc.perform(get("/api/v1/playlists/1"))
            .andExpect(status().isOk)
        // Add assertions for the expected response
    }

    @Test
    fun `로그인 상태로 플레이리스트 조회`() {
        mvc.perform(get("/api/v1/playlists/1")
            .header("Authorization", "Bearer gnirps"))
            .andExpect(status().isOk)
        // Add assertions for the expected response
    }

    @Test
    fun `플레이리스트 좋아요`() {
        mvc.perform(post("/api/v1/playlists/1/likes")
            .header("Authorization", "Bearer gnirps"))
            .andExpect(status().isOk)
        // Add assertions for the expected response
    }


}

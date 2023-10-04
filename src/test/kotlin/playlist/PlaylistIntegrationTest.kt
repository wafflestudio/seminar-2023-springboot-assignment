package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {
    @Test
    fun `플레이리스트그룹 조회`() {
        mvc.perform(
            get("/api/v1/playlist-groups")
        )
            .andExpect(status().isOk())
    }
    @Test
    fun `플레이리스트 조회 해당하는 플레이리스트가 없을 경우 404 응답`() {
        val playlistId = 404404404L
        mvc.perform(
            get("/api/v1/playlists/{id}", playlistId)
        )
            .andExpect(status().isNotFound())
    }
    @Test
    fun `플레이리스트 좋아요 이미 좋아요 눌렀을 경우 400 응답 해당하는 플레이리스트가 없을경우 404 응답`() {
        val playlistId = 2L
        val playlistIdd = 404404404L

        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-4",
                            "password" to "correct",
                            "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/signin")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-4",
                            "password" to "correct",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            get("/api/v1/users/me")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-4".reversed()}")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/playlists/{id}/likes", playlistId)
                .header("Authorization", "Bearer ${"test-${javaClass.name}-4".reversed()}")
        )
            .andExpect(status().isOk())

        mvc.perform(
            post("/api/v1/playlists/{id}/likes", playlistId)
                .header("Authorization", "Bearer ${"test-${javaClass.name}-4".reversed()}")
        )
            .andExpect(status().isBadRequest())

        mvc.perform(
            post("/api/v1/playlists/{id}/likes", playlistIdd)
                .header("Authorization", "Bearer ${"test-${javaClass.name}-4".reversed()}")
        )
            .andExpect(status().isNotFound())
    }
    @Test
    fun `플레이리스트 좋아요 취소 좋아요를 누르지 않은 플레이리스트 좋아요 취소할 경우 400 응답`() {
        // 좋아요를 누르지 않은 플레이리스트 선택
        val playlistId = 4L

        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-5",
                            "password" to "correct",
                            "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/signin")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-5",
                            "password" to "correct",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            get("/api/v1/users/me")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-5".reversed()}")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/playlists/{id}/likes", playlistId)
                .header("Authorization", "Bearer ${"test-${javaClass.name}-5".reversed()}")
        )
            .andExpect(status().isOk())

        mvc.perform(
            delete("/api/v1/playlists/{id}/likes", playlistId)
                .header("Authorization", "Bearer ${"test-${javaClass.name}-5".reversed()}")
        )
            .andExpect(status().isOk())

        mvc.perform(
            delete("/api/v1/playlists/{id}/likes", playlistId)
                .header("Authorization", "Bearer ${"test-${javaClass.name}-5".reversed()}")
        )
            .andExpect(status().isBadRequest())
    }
}

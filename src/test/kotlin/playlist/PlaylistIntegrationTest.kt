package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
        private val mvc: MockMvc,
        private val mapper: ObjectMapper,
) {
    //playlistLike
    //존재하지 않는 playlist 좋아요 404
    //좋아요 중복 409
    //좋아요 취소 중복 409

    //playlist
    //플레이리스트 그룹 조회
    //존재하지 않는 playlist 조회 404

    fun signIn() {
        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/signup")
                        .content(
                                mapper.writeValueAsString(
                                        mapOf(
                                                "username" to "test-${javaClass.name}-1",
                                                "password" to "correct",
                                                "image" to "https://wafflestudio.com/images/icon_intro.svg"
                                        )
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        )

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/signin")
                        .content(
                                mapper.writeValueAsString(
                                        mapOf(
                                                "username" to "test-${javaClass.name}-1",
                                                "password" to "correct",
                                        )
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        )
    }

    @Test
    fun `존재하지 않는 플레이리스트에 좋아요를 누를 경우 404 응답`() {
        signIn()

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/playlists/{id}/likes", 100000000000)
                        .header("Authorization", "Bearer ${"test-${javaClass.name}-1".reversed()}")
        )
                .andExpect(MockMvcResultMatchers.status().`is`(404))
    }

    @Test
    fun `이미 좋아요를 누른 플레이리스트에 또 좋아요를 누를 경우 409 응답`() {
        signIn()

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/playlists/{id}/likes", 1)
                        .header("Authorization", "Bearer ${"test-${javaClass.name}-1".reversed()}")
        )
                .andExpect(MockMvcResultMatchers.status().`is`(200))

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/playlists/{id}/likes", 1)
                        .header("Authorization", "Bearer ${"test-${javaClass.name}-1".reversed()}")
        )
                .andExpect(MockMvcResultMatchers.status().`is`(409))
    }

    @Test
    fun `좋아요를 누르지 않은 플레이리스트에 좋아요 취소를 누를 경우 409 응답`() {
        signIn()

        mvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/playlists/{id}/likes", 2)
                        .header("Authorization", "Bearer ${"test-${javaClass.name}-1".reversed()}")
        )
                .andExpect(MockMvcResultMatchers.status().`is`(409))
    }

    @Test
    fun `플레이리스트 그룹 조회`() {
        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/playlist-groups")
        )
                .andExpect(MockMvcResultMatchers.status().`is`(200))
    }

    @Test
    fun `존재하지 않는 플레이리스트를 조회할 경우 404 응답`() {
        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/playlists/{id}", 1)
        )
                .andExpect(MockMvcResultMatchers.status().`is`(200))
        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/playlists/{id}", 100000000000)
        )
                .andExpect(MockMvcResultMatchers.status().`is`(404))
    }
}


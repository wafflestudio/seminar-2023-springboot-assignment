package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper
) {
    @Test
    fun `존재하지 않는 플레이리스트 요청 시 400 응답을 내려준다`(){
        mvc.perform(
            post("/api/v1/signup")
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
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/signin")
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
            .andExpect(status().`is`(200))

        mvc.perform(
            get("/api/v1/playlists/404040404")
        )
            .andExpect(status().`is`(400))

        mvc.perform(
            get("/api/v1/playlists/1")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.playlist.id").value(1L))
            .andExpect(jsonPath("$.isLiked").value(false))

        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-1".reversed()}")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            get("/api/v1/playlists/1")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-1".reversed()}")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.playlist.id").value(1L))
            .andExpect(jsonPath("$.isLiked").value(true))
    }

    @Test
    fun `좋아요한 플레이리스트를 다시 좋아요할 경우 401 응답을 내려준다`(){
        mvc.perform(
            post("/api/v1/signup")
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
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/signin")
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
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-1".reversed()}")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-1".reversed()}")
        )
            .andExpect(status().`is`(401))
    }

    @Test
    fun `좋아요를 하지 않은 플레이리스트의 좋아요를 취소할 때 404 응답을 내려준다`(){
        mvc.perform(
            post("/api/v1/signup")
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
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/signin")
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
            .andExpect(status().`is`(200))

        mvc.perform(
            delete("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-1".reversed()}")
        )
            .andExpect(status().`is`(404))

        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-1".reversed()}")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            delete("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-1".reversed()}")
        )
            .andExpect(status().`is`(200))
    }
}

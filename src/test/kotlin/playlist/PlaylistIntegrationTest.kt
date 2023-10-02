package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper
) {
    @BeforeEach
    fun 회원가입() {
        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test",
                            "password" to "qwer1234",
                            "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().`is`(200))
    }

    @Test
    fun `플레이리스트 그룹 조회`() {
        mvc.perform(
            get("/api/v1/playlist-groups")
        ).andExpect(status().`is`(200))
    }

    @Test
    fun `플레이리스트 조회`() {
        mvc.perform(
            get("/api/v1/playlists/1")
        ).andExpect(status().`is`(200))
    }

    @Test
    fun `존재하지 않는 플레이리스트를 조회한 경우 404`() {
        mvc.perform(
            get("/api/v1/playlists/404404404")
        ).andExpect(status().`is`(404))
    }

    @Test
    fun `로그아웃 상태에서 플레이리스트 좋아요 되어있지 않아야 함`() {
        mvc.perform(
                get("/api/v1/playlists/1")
        ).andExpect(jsonPath("$.isLiked").value(false))
    }

    @Test
    fun `로그아웃 상태에서 플레이리스트 좋아요 등록 or 해제 시 401`() {
        mvc.perform(
            post("/api/v1/playlists/1/likes")
        ).andExpect(status().`is`(401))
    }

    @Test
    fun `로그인 상태에서 플레이리스트 좋아요 등록 or 해제`() {
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer tset")
        ).andExpect(status().`is`(200))

        mvc.perform(
            delete("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer tset")
        ).andExpect(status().`is`(200))
    }

    @Test
    fun `로그인 상태에서 플레이리스트 중복 좋아요 등록 or 해제 시 409`() {
        mvc.perform(
                post("/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer tset")
        ).andExpect(status().`is`(200))

        mvc.perform(
                post("/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer tset")
        ).andExpect(status().`is`(409))

        mvc.perform(
                delete("/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer tset")
        ).andExpect(status().`is`(200))

        mvc.perform(
                delete("/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer tset")
        ).andExpect(status().`is`(409))
    }
}

package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {

    @Test
    fun `플레이리스트 그룹 조회`() {
        mvc.perform(
            get("/api/v1/playlist-groups")
        ).andExpect(status().`is`(200))
    }

    @Test
    fun `플레이스트 조회 실패시 404 응답 내려준다`() {
        mvc.perform(
            get("/api/v1/playlists/1")
        ).andExpect(status().`is`(200))

        // playlist doesn't exist
        mvc.perform(
            get("/api/v1/playlist/300")
        ).andExpect(status().`is`(404))
    }

    @Test
    fun `비로그인 상황, Like, UnLike 요청시 401 응답 내려준다`() {
        mvc.perform(
            post("/api/v1/playlists/1/likes")
        ).andExpect(status().`is`(401))
    }

    @Test
    fun `로그인 상황, 플레이리스트 Like, UnLike 중복 요청시 403 응답 내려준다`() {
        // 회원 가입
        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-2",
                            "password" to "correct",
                            "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))

        // Unlike Playlist에 unLike 요청
        mvc.perform(
            delete("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-2".reversed()}")
        ).andExpect(status().`is`(403))

        // Unlike Playlist에 Like 요청
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-2".reversed()}")
        ).andExpect(status().`is`(200))

        // Like Playlist에 Like 요청
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-2".reversed()}")
        ).andExpect(status().`is`(403))

        // Like Playlist에 Unlike 요청
        mvc.perform(
            delete("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-2".reversed()}")
        ).andExpect(status().`is`(200))
    }

}

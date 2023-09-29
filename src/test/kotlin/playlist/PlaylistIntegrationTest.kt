package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import com.wafflestudio.seminar.spring2023.user.controller.SignInResponse
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

    private fun signUpAndSignIn(): String {
        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                                "username" to "test-user",
                                "password" to "test-password",
                                "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                    .contentType(MediaType.APPLICATION_JSON)
        )

        val signInResponse = mvc.perform(
            post("/api/v1/signin")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-user",
                            "password" to "test-password",
                        )
                    )
                )
                    .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))
            .andReturn()

        val content = signInResponse.response.contentAsString
        val signInResult = mapper.readValue(content, SignInResponse::class.java)

        return signInResult.accessToken
    }
    @Test
    fun `playlist 그룹 조회 시 200 응답을 내려준다`() {
        mvc.perform(
            get("/api/v1/playlist-groups")
        )
            .andExpect(status().`is`(200))
    }

    @Test
    fun `미로그인 상태로 플레이리스트 조회`() {
        // Act & Assert
        mvc.perform(get("/api/v1/playlists/1"))
            .andExpect(status().`is`(200))
    }

    @Test
    fun `로그인 상태로 플레이리스트 조회`() {
        val accessToken = signUpAndSignIn()

        // Act & Assert
        mvc.perform(
            get("/api/v1/playlists/1")
                    .header("Authorization", "Bearer $accessToken")
        )
            .andExpect(status().`is`(200))
    }

    @Test
    fun `playlist 조회 시 playlist가 존재하지 않으면 404 응답을 내려준다`() {
        mvc.perform(
            get("/api/v1/playlists/9999")
        )
            .andExpect(status().`is`(404))
    }

    @Test
    fun `playlist에 좋아요를 누를 시 좋아요가 이미 존재하면 409 응답을 내려준다`() {
        val accessToken = signUpAndSignIn()
        // First like
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer $accessToken")
        )
                .andExpect(status().`is`(200))
        // Second like should fail
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer $accessToken")
        )
            .andExpect(status().`is`(409))
    }


    @Test
    fun `playlist에 좋아요를 누를 시 playlist가 존재하지 않으면 404 응답을 내려준다`() {
        val accessToken = signUpAndSignIn()
        // Like a non-existing playlist
        mvc.perform(
            post("/api/v1/playlists/9999/likes")
                    .header("Authorization", "Bearer $accessToken")
        )
            .andExpect(status().`is`(404))
    }

    @Test
    fun `playlist에 좋아요를 취소할 시 playlist가 없으면 404 응답을 내려준다`() {
        val accessToken = signUpAndSignIn()

        // Dislike without liking first should fail
        mvc.perform(
                delete("/api/v1/playlists/9999/likes")
                        .header("Authorization", "Bearer $accessToken")
        )
                .andExpect(status().`is`(404))
    }

    @Test
    fun `playlist에 좋아요를 취소할 시 좋아요가 없었다면 409 응답을 내려준다`() {
        val accessToken = signUpAndSignIn()

        // Dislike without liking first should fail
        mvc.perform(
            delete("/api/v1/playlists/2/likes")
                    .header("Authorization", "Bearer $accessToken")
        )
            .andExpect(status().`is`(409))
    }

}

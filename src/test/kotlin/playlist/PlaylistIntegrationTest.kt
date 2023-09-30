package com.wafflestudio.seminar.spring2023.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.wafflestudio.seminar.spring2023.user.controller.SignInResponse
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
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {

    private fun signUpandSignIn(): String {

        mvc.perform(
            post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "temp_user",
                            "password" to "temp_password",
                            "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
        val signInResponse =
            mvc.perform(
                post("/api/v1/signin")
                    .content(
                        mapper.writeValueAsString(
                            mapOf(
                                "username" to "temp_user",
                                "password" to "temp_password",
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
    fun `없는 playlist를 조회하면 404 응답을 내려준다`() {
        mvc.perform(
            get("/api/v1/playlist/10000"))
            .andExpect(status().`is`(404))
    }

    @Test
    fun `playlist에 좋아요를 이미 누른 후 또 누르면 409 응답을 내려준다`() {
        val accessToken = signUpandSignIn()

        mvc.perform(
            post("/api/v1/playlists/3/likes")
                .header("Authorization", "Bearer $accessToken")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/playlists/3/likes")
                .header("Authorization", "Bearer $accessToken")
        )
            .andExpect(status().`is`(409))

    }

    @Test
    fun `playlist 좋아요를 이미 누른 후 좋아요를 취소하면 200 응답을 내려준다`() {
        val accessToken = signUpandSignIn()

        mvc.perform(
            post("/api/v1/playlists/3/likes")
                .header("Authorization", "Bearer $accessToken")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            delete("/api/v1/playlists/3/likes")
                .header("Authorization", "Bearer $accessToken")
        )
            .andExpect(status().`is`(200))
    }

    @Test
    fun `playlist 좋아요를 누르지 않은 상태에서 좋아요를 누르면 404 응답을 내려준다`() {
        val accessToken = signUpandSignIn()

        mvc.perform(
            delete("/api/v1/playlists/4/likes")
                .header("Authorization", "Bearer $accessToken")
        )
            .andExpect(status().`is`(409))
    }

    @Test
    fun `로그인시 playlist 조회하면 200 응답을 내려준다`() {
        val accessToken = signUpandSignIn()

        mvc.perform(
            get("/api/v1/playlists/1")
                .header("Authorization", "Bearer $accessToken")
        )
            .andExpect(status().`is`(200))
    }

    @Test
    fun `미로그인시 playlist 조회하면 200 응답을 내려준다`() {
        mvc.perform(get("/api/v1/playlists/2"))
            .andExpect(status().`is`(200))
    }
}





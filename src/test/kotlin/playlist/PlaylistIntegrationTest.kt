package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import com.wafflestudio.seminar.spring2023.user.controller.SignInResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,

) {
    companion object {
        var token: String? = null
    }
    private fun signUpAndSignIn(): String {
        if (token == null) {
            mvc.perform(
                post("/api/v1/signup")
                    .content(
                        mapper.writeValueAsString(
                            mapOf(
                                "username" to "test-${javaClass.name}-1",
                                "password" to "spring",
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
                                "username" to "test-${javaClass.name}-1",
                                "password" to "spring",
                            )
                        )
                    )
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().`is`(200))
                .andReturn()

            val content = signInResponse.response.contentAsString
            val signInResult = mapper.readValue(content, SignInResponse::class.java)
            token = signInResult.accessToken
        }
        return token!!
    }

    @Test
    fun `플레이리스트 그룹을 호출한다`() {
        mvc.perform(
            get("/api/v1/playlist-groups")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.groups").exists())
    }

    @Test
    fun `비로그인 상태에서 playlist 조회시 플레이리스트의 하트는 비어있다`() {
        mvc.perform(
            get("/api/v1/playlists/1")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.isLiked").value(false))
    }

    @Test
    fun `존재하지 않는 playlist 조회 시 404 응답을 내려준다`() {
        mvc.perform(
            get("/api/v1/playlists/298475")
        )
            .andExpect(status().`is`(404))
    }

    @Test
    fun `playlist에 좋아요 요청을 보냈을 때, 이미 좋아요가 되어있다면 409 응답을 내려준다`() {
        val token = signUpAndSignIn()
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().`is`(201))

        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().`is`(409))
    }

    @Test
    fun `playlist에 좋아요 삭제 요청을 보냈을 때, 이미 좋아요가 존재하지 않는다면 409 응답을 내려준다`(){
        val token = signUpAndSignIn()
        mvc.perform(
            post("/api/v1/playlists/2/likes")
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().`is`(201))

        mvc.perform(
            delete("/api/v1/playlists/2/likes")
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            delete("/api/v1/playlists/2/likes")
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().`is`(409))
    }
}

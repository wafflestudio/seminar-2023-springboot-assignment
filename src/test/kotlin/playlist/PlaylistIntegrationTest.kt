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


@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
        private val mvc: MockMvc,
) {
    @Test
    fun `플레이리스트 그룹 조회시 200 응답`(){
        mvc.perform(
                get("/api/v1/playlist-groups")
        ).andExpect(status().`is`(200))
    }
    @Test
    fun `플레이리스트 조회 성공시 200, 실패시 404 응답`(){
        mvc.perform(
                get("/api/v1/playlists/1")
        ).andExpect(status().`is`(200))
        mvc.perform(
                get("/api/v1/playlists/9999999")
        ).andExpect(status().`is`(404))
    }
    @Test
    fun `존재하지 않는 플레이리스트에 좋아요 시도시 404 응답`(){
        mvc.perform(
                post("/api/v1/playlists/1/likes")
                        .header("Authorization","Bearer gnirps")
        ).andExpect(status().`is`(200))
        mvc.perform(
                post("/api/v1/playlists/9999999/likes")
                        .header("Authorization","Bearer gnirps")
        ).andExpect(status().`is`(404))

    }
    @Test
    fun `이미 플레이리스트 좋아요 눌려있는 경우 409 응답`(){
        mvc.perform(
                post("/api/v1/playlists/1/likes")
                        .header("Authorization","Bearer gnirps")
        ).andExpect(status().`is`(200))
        mvc.perform(
                post("/api/v1/playlists/1/likes")
                        .header("Authorization","Bearer gnirps")
        ).andExpect(status().`is`(409))
    }
    @Test
    fun `좋아요 안누른 플레이리스트 좋아요 취소하는 경우 409 응답`(){
        mvc.perform(
                delete("/api/v1/playlists/1/likes")
                        .header("Authorization","Bearer gnirps")
        ).andExpect(status().`is`(409))
    }
}

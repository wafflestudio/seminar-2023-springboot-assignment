package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {
    @Test
    fun `플레이리스트 그룹을 조회하면 200 응답과 그룹 정보를 내려준다`() {
        val result = mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlist-groups")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().response.contentAsString
        print(result);
    }

    @Test
    fun `미로그인 상태로 플레이리스트를 조회하면 200 응답과 정보를 내려준다`() {
        val result = mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlists/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().response.contentAsString
        val jsonNode = mapper.readTree(result)
        assertThat(jsonNode.get("isLiked")?.asText()).isEqualTo("false")
    }

    @Test
    fun `로그인 상태로 플레이리스트에 좋아요 요청을 보내면 200 응답을 내려주고, 다시 좋아요 요청을 보내면 409 응답을 내려준다`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/playlists/1/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        val result = mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlists/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().response.contentAsString
        val jsonNode = mapper.readTree(result)
        assertThat(jsonNode.get("isLiked")?.asText()).isEqualTo("true")

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/playlists/1/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(409))
    }

    @Test
    fun `로그인 상태로 플레이리스트에 좋아요 취소 요청을 보내면 200 응답을 내려준다`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/playlists/1/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        mvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/playlists/1/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
    }

    @Test
    fun `로그인 상태로 좋아하지 않는 플레이리스트에 좋아요 취소 요청을 보내면 409 응답을 내려준다`() {
        val result = mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlists/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().response.contentAsString
        val jsonNode = mapper.readTree(result)
        assertThat(jsonNode.get("isLiked")?.asText()).isEqualTo("false")

        mvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/playlists/1/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(409))
    }

    @Test
    fun `존재하지 않는 플레이리스트에 좋아요 혹은 취소 요청을 보내면 409 응답을 내려준다`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/playlists/12121212121212121/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        mvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/playlists/12121212121212121/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(404))
    }
}

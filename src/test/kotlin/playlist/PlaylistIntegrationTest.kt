package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `공개된 플레이리스트 그룹을 조회`() {
        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/playlist-groups")
        )
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("\$.groups[0].id").value("1"),
                        jsonPath("\$.groups[1].id").value("3")
                )
    }

    @Test
    fun `미로그인 상태로 플레이리스트 조회`() {
        mvc.perform(
                MockMvcRequestBuilders.get("http://localhost:8080/api/v1/playlists/404404404")
        ).andExpect(status().`is`(404))

        mvc.perform(
                MockMvcRequestBuilders.get("http://localhost:8080/api/v1/playlists/1")
        )
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("\$.playlist.songs[0].id").value(2),
                        jsonPath("\$.playlist.songs[1].id").value(8)
                )
    }

    @Test
    fun `로그인 상태로 플레이리스트 조회`() {
        mvc.perform(
                MockMvcRequestBuilders
                        .get("http://localhost:8080/api/v1/playlists/1")
                        .header("Authorization", "Bearer gnirps")
        )
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("\$.playlist.songs[0].id").value(2),
                        jsonPath("\$.playlist.songs[1].id").value(8)
                )
    }

    @Test
    fun `플레이리스트 좋아요`() {
        mvc.perform(
                MockMvcRequestBuilders.post("http://localhost:8080/api/v1/playlists/1/likes")
        ).andExpect(status().`is`(401))

        mvc.perform(
                MockMvcRequestBuilders
                        .post("http://localhost:8080/api/v1/playlists/404404404/likes")
                        .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(404))

        mvc.perform(
                MockMvcRequestBuilders
                        .post("http://localhost:8080/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(200))

        mvc.perform(
                MockMvcRequestBuilders
                        .post("http://localhost:8080/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(409))
    }

    @Test
    fun `플레이리스트 좋아요 취소`() {
        mvc.perform(
                MockMvcRequestBuilders.delete("http://localhost:8080/api/v1/playlists/1/likes")
        ).andExpect(status().`is`(401))

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("http://localhost:8080/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(409))

        mvc.perform(
                MockMvcRequestBuilders
                        .post("http://localhost:8080/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(200))

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("http://localhost:8080/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(200))
    }
}

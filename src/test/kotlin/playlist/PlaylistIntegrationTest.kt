package com.wafflestudio.seminar.spring2023.playlist

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `플레이리스트 그룹 조회`() {
        mvc.perform(
            get("/api/v1/playlist-groups")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.groups[0].title").value("Spotify 플레이리스트"))
            .andExpect(jsonPath("$.groups[0].playlists[0].title").value("Today's Top Hits"))
    }

    @Test
    fun `로그아웃 플레이리스트 조회`() {
        mvc.perform(
            get("/api/v1/playlists/1")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.playlist.title").value("Today's Top Hits"))
            .andExpect(jsonPath("$.playlist.subtitle").value("Olivia Rodrigo is on top of the Hottest 50!"))
            .andExpect(jsonPath("$.isLiked").value(false))
    }

    @Test
    fun `로그인 플레이리스트 조회`() {
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().isOk)
        mvc.perform(
            get("/api/v1/playlists/1")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.playlist.title").value("Today's Top Hits"))
            .andExpect(jsonPath("$.isLiked").value("true"))

    }

    @Test
    fun `플레이리스트 좋아요`() {
        // like playlist
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().isOk)

        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(400))

        mvc.perform(
            post("/api/v1/playlists/100000/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(404))
    }

    @Test
    fun `플레이리스트 좋아요 취소`() {
        // like playlist
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().isOk)

        // un-like playlist
        mvc.perform(
            delete("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().isOk)

        mvc.perform(
            delete("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(400))

        mvc.perform(
            delete("/api/v1/playlists/100000/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(400))
    }
}

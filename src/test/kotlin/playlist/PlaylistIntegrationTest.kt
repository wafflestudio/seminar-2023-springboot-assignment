package com.wafflestudio.seminar.spring2023.playlist

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `플레이리스트 그룹 조회`() {
        mvc.perform(
                get("/api/v1/playlist-groups")
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.groups[0].title").value("Spotify 플레이리스트"))
                .andExpect(jsonPath("$.groups[0].playlists[0].title").value("Today's Top Hits"))
    }
    @Test
    fun `비로그인 상태로 플레이리스트 조회 시 isLiked = false`() {
        mvc.perform(
                get("/api/v1/playlists/1")
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.playlist.title").value("Today's Top Hits"))
                .andExpect(jsonPath("$.playlist.songs[0].title").value("bad idea right?"))
                .andExpect(jsonPath("$.isLiked").value(false))
    }
    @Test
    fun `로그인 상태로 좋아요 누른 플레이리스트 조회 시 isLiked = true`() {
        mvc.perform(
                post("/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        )

        mvc.perform(
                get("/api/v1/playlists/1")
                        .header("Authorization", "Bearer gnirps")
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.playlist.title").value("Today's Top Hits"))
                .andExpect(jsonPath("$.isLiked").value(true))

        mvc.perform(
                get("/api/v1/playlists/2")
                        .header("Authorization", "Bearer gnirps")
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.playlist.title").value("RapCaviar"))
                .andExpect(jsonPath("$.isLiked").value(false))
    }
    @Test
    fun `좋아요 누른 플레이리스트에 다시 좋아요 누르기`() {
        mvc.perform(
                post("/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        )
                .andExpect(status().`is`(200))

        mvc.perform(
                post("/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        )
                .andExpect(status().`is`(409))
    }
    @Test
    fun `좋아요 누르지 않은 플레이리스트에 좋아요 취소 누르기`() {
        mvc.perform(
                delete("/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        )
                .andExpect(status().`is`(409))
    }
}

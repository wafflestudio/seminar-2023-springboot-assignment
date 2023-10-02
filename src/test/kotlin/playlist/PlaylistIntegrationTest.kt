package com.wafflestudio.seminar.spring2023.playlist

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun testGetPlaylistGroups() {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/playlist-groups"))
            .andExpect(status().isOk)
    }

    @Test
    fun testGetPlaylistUnauthenticated() {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/playlists/1"))
            .andExpect(status().isOk)
    }

    @Test
    fun testGetPlaylistAuthenticated() {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/playlists/1")
            .header("Authorization", "Bearer gnirps"))
            .andExpect(status().isOk)
    }

    @Test
    fun testLikePlaylist() {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/playlists/1/likes")
            .header("Authorization", "Bearer gnirps"))
            .andExpect(status().isOk)
    }

    @Test
    fun testUnlikePlaylist() {
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/playlists/1/likes")
            .header("Authorization", "Bearer gnirps"))
            .andExpect(status().isOk)
    }
}

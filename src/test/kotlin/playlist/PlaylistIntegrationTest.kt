package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper
) {

    @Test
    fun `플레이리스트 그룹 조회`() {
        mvc.perform(
            get("/api/v1/playlist-groups")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.groups[0].title").value("Spotify 플레이리스트"))
            .andExpect(jsonPath("$.groups[1].title").value("집중"))
            .andExpect(jsonPath("$.groups[0].playlists[1].title").value("RapCaviar"))
    }

    @Test
    fun `미로그인 상태로 플레이리스트 조회`() {
        mvc.perform(
            get("/api/v1/playlists/1")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.playlist.title").value("Today's Top Hits"))
            .andExpect(jsonPath("$.playlist.subtitle").value("Olivia Rodrigo is on top of the Hottest 50!"))
            .andExpect(jsonPath("$.isLiked").value(false))
    }

    @Test
    fun `로그인 상태로 플레이리스트 조회`() {

        val username = "spring"

        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer ${username.reversed()}")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            get("/api/v1/playlists/1")
                .header("Authorization", "Bearer ${username.reversed()}")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.playlist.title").value("Today's Top Hits"))
            .andExpect(jsonPath("$.isLiked").value(true))
    }

    @Test
    fun `플레이리스트 좋아요 취소`() {
        val username = "spring"
        val playlistId = 3

        mvc.perform(
            post("/api/v1/playlists/$playlistId/likes")
                .header("Authorization", "Bearer ${username.reversed()}")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            get("/api/v1/playlists/$playlistId")
                .header("Authorization", "Bearer ${username.reversed()}")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.playlist.title").value("All Out 2010s"))
            .andExpect(jsonPath("$.isLiked").value(true))

        mvc.perform(
            delete("/api/v1/playlists/$playlistId/likes")
                .header("Authorization", "Bearer ${username.reversed()}")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            get("/api/v1/playlists/$playlistId")
                .header("Authorization", "Bearer ${username.reversed()}")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.playlist.title").value("All Out 2010s"))
            .andExpect(jsonPath("$.isLiked").value(false))
    }

    @Test
    fun `존재하지 않는 플레이리스트를 조회하면 404 응답을 준다`() {
        mvc.perform(
            get("/api/v1/playlists/1234567")
        )
            .andExpect(status().`is`(404))
    }

    @Test
    fun `이미 좋아요한 플레이리스트를 좋아요하면 400 응답을 준다`() {
        val username = "spring"
        val playlistId = 4

        mvc.perform(
            post("/api/v1/playlists/$playlistId/likes")
                .header("Authorization", "Bearer ${username.reversed()}")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            post("/api/v1/playlists/$playlistId/likes")
                .header("Authorization", "Bearer ${username.reversed()}")
        )
            .andExpect(status().`is`(400))
    }

    @Test
    fun `좋아요하지 않은 플레이리스트를 좋아요 취소하면 400 응답을 준다`() {

        val username = "spring"
        val playlistId = 1

        mvc.perform(
            delete("/api/v1/playlists/$playlistId/likes")
                .header("Authorization", "Bearer ${username.reversed()}")
        )
            .andExpect(status().`is`(400))
    }
}

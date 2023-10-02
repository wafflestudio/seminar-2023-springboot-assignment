package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {
    @Test
    fun `플레이리스트 그룹 조회`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlist-groups")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.groups[0].title").value("Spotify 플레이리스트"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.groups[0].playlists[0].title").value("Today's Top Hits"))
    }

    @Test
    fun `미로그인 상태로 플레이리스트 조회`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlists/1")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.playlist.songs[0].title").value("bad idea right?"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.isLiked").value(false))

    }
    @Test
    fun `로그인 상태로 플레이리스트 조회`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlists/1").header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.playlist.songs[0].title").value("bad idea right?"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.isLiked").value(false))
    }
    @Test
    fun `존재하지 않는 플레이리스트 조회시 404 응답을 내려준다`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlists/12345").header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(404))
    }
    @Test
    fun `존재하지 않는 플레이리스트에 좋아요를 요청할 시 404 응답을 내려준다`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/playlists/12345/likes").header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(404))
    }
    @Test
    fun `처음 좋아요시 200, 그 다음부터는 409 응답을 내려준다`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/playlists/1/likes").header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/playlists/1/likes").header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(409))
    }

    @Test
    fun `좋아요가 아닌 항목 좋아요 취소시 409 응답을 내려준다`() {
        mvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/playlists/1/likes").header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(409))
    }

    @Test
    fun `좋아요가 된 항목 취소시 200 응답을 내려준다`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/playlists/1/likes").header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        mvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/playlists/1/likes").header("Authorization", "Bearer gnirps")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
    }
}

package com.wafflestudio.seminar.spring2023.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {
    @Test
    fun `회원 및 비회원 상태에서 플레이리스트 조회`(){
        mvc.perform(
                get("/api/v1/playlists/9999999")
                        .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(404))

        mvc.perform(
                get("/api/v1/playlists/1")
        )
                .andExpect(status().`is`(200))
                .andExpect(jsonPath("playlist.subtitle").value("Olivia Rodrigo is on top of the Hottest 50!"))

        mvc.perform(
                get("/api/v1/playlists/1")
                        .header("Authorization", "Bearer gnirps")
        )
                .andExpect(status().`is`(200))
                .andExpect(jsonPath("playlist.subtitle").value("Olivia Rodrigo is on top of the Hottest 50!"))
                .andExpect(jsonPath("isLiked").value("false"))

    }
    @Test
    fun `플레이리스트 그룹 조회`(){
        mvc.perform(
                get("/api/v1/playlist-groups")
        )
                .andExpect(status().`is`(200))
                .andExpect(jsonPath("$.groups.[0].title").value("Spotify 플레이리스트"))
                .andExpect(jsonPath("$.groups.[1].title").value("집중"))
                .andExpect(jsonPath("$.groups.[1].playlists.[5].title").value("Reading Adventure"))
    }
    @Test
    fun `좋아요를 누르려는 플레이리스트가 존재하지 않으면 404 응답을 내려준다`(){
        mvc.perform(
                post("/api/v1/playlists/99900000/likes")
                        .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(404))
    }
    //@Test
    fun `좋아요 누른 플레이리스트에 다시 좋아요 요청이 들어오거나 누르지 않았는데 취소 요청이 들어오면 400 응답을 내려준다`(){
        mvc.perform(
                post("/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(200))
        mvc.perform(
                post("/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(400))
        mvc.perform(
                delete("/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(200))
        mvc.perform(
                delete("/api/v1/playlists/1/likes")
                        .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(400))
    }
}

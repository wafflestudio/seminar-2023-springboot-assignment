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
    private val mapper : ObjectMapper,
) {
    @Test
    fun `플레이리스트 그룹 조회가 잘 되었는지 확인`(){
        mvc.perform(
            get("/api/v1/playlist-groups")
        ).andExpect(status().`is`(200))
    }

    @Test
    fun `플레이리스트 조회 `() {
        //unauthorized
        mvc.perform(
            get("/api/v1/playlists/1")
        )
            .andExpect(status().isOk)

        //authorized
        mvc.perform(
            get("/api/v1/playlists/1")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().isOk)

        //playlist is not found
        mvc.perform(
            get("/api/v1/playlists/10000")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(400))
    }

    @Test
    fun `플레이리스트에 좋아요가 잘 되는지 확인`(){
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(200))

        //playlist not found
        mvc.perform(
            post("/api/v1/playlists/404404404/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(400))

        //playlist already liked
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(401))

    }
    @Test
    fun `플레이리스트 좋아요 취소가 잘 되는지 확인`(){
        mvc.perform(
            post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(200))

        mvc.perform(
            delete("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(200))

        //playlist never liked
        mvc.perform(
            delete("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().`is`(402))
    }

}

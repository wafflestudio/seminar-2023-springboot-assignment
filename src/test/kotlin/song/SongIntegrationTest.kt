package com.wafflestudio.seminar.spring2023.song

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    val keyword = "Seven"
    @Test
    fun `Seven이 포함된 곡 검색`() {
        mvc.perform(
                get("/api/v1/songs?keyword=${keyword}")
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.songs[1].id").value(33L))
                .andExpect(jsonPath("$.songs[1].duration").value(184))
                .andExpect(jsonPath("$.songs[1].artists[0].name").value("정국"))
    }
    @Test
    fun `Seven이 포함된 앨범 검색`() {
        mvc.perform(
                get("/api/v1/albums?keyword=${keyword}")
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.albums[1].id").value(5L))
                .andExpect(jsonPath("$.albums[1].artist.name").value("정국"))
    }
}

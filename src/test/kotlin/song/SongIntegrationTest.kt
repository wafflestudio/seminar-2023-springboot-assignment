package com.wafflestudio.seminar.spring2023.song

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `곡 조희`() {
        mvc.perform(
            get("/api/v1/songs?keyword=Together")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.songs[0].id").value(1203L))
            .andExpect(jsonPath("$.songs[1].id").value(1484L))
            .andExpect(jsonPath("$.songs[0].album").value("A History Of Scotland"))
            .andExpect(jsonPath("$.songs[0].artists[0].id").value(347L))
    }

    @Test
    fun `앨범 조회`() {
        mvc.perform(
            get("/api/v1/albums?keyword=Seven")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.albums[0].id").value(29L))
            .andExpect(jsonPath("$.albums[1].id").value(5L))
            .andExpect(jsonPath("$.albums[1].artist.name").value("정국"))
    }
}

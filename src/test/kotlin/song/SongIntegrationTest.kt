package com.wafflestudio.seminar.spring2023.song

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `특정 키워드로 곡을 조회할 수 있다`() {
        mvc.perform(
            get("/api/v1/songs")
                .param("keyword", "lucky")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.songs").exists())
    }

    @Test
    fun `특정 키워드로 앨범을 조회할 수 있다`() {
        mvc.perform(
            get("/api/v1/albums")
                .param("keyword", "lucky")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.albums").exists())
    }
}
